#!/bin/bash

# Function to execute commands with logging
execute_command() {
    local command="$1"
    echo "Executing: $command"
    if eval "$command"; then
        echo "Command executed successfully"
    else
        echo "Error executing command: $command" >&2
        exit 1
    fi
}

# Image tag to deploy (defaults to latest; pass a specific tag, e.g. sha-<commit-sha>, to roll back).
# A bare 40-char commit SHA is normalized to the sha-<commit-sha> tag docker/metadata-action pushes.
image_tag="${1:-latest}"
if [[ "$image_tag" =~ ^[0-9a-f]{40}$ ]]; then
    image_tag="sha-${image_tag}"
fi
export WEATHER_IMAGE_TAG="$image_tag"

# Start deploy weather script
echo "Start deploy weather script (image tag: $WEATHER_IMAGE_TAG)."

# Commands to execute. The named postgres_data volume survives `down`, so the database keeps
# its contents across deploys — only the containers are recreated.
compose_down="docker compose -f cprod.yml down"
compose_pull="docker compose -f cprod.yml pull"
compose_up="docker compose -f cprod.yml up -d"
# Recreated containers join the proxy network with fresh IPs, which the reverse proxy only
# resolves at startup — without this restart it keeps proxying to the containers that are gone.
proxy_restart="docker restart reverse-proxy"

# Execute the commands
execute_command "$compose_down"
execute_command "$compose_pull"
execute_command "$compose_up"
execute_command "$proxy_restart"

# Clean up images left behind by the deploy
echo 'Cleaning up unused images.'
execute_command "docker image prune -f"

# Compare full-length IDs from docker inspect/--no-trunc; short IDs from different
# docker/compose commands aren't guaranteed to be formatted consistently.
for service in weather-api weather-ui; do
    running_image_id=$(docker inspect --format '{{.Image}}' "$service" 2>/dev/null)
    old_image_ids=$(docker images "oskarwestmeijer/$service" --no-trunc --format '{{.ID}}' | sort -u | grep -vF "$running_image_id")
    if [ -n "$old_image_ids" ]; then
        execute_command "docker image rm -f $(echo "$old_image_ids" | tr '\n' ' ')"
    else
        echo "No unused $service images to remove."
    fi
done

# Finish deploy weather script
echo 'Finish deploy weather script.'
