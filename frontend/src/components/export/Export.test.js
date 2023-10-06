import { render, screen } from "@testing-library/react";
import Export from "./Export";

it("page init after locations response", () => {
    render(<Export />);
    expect(screen.getByText('Requesting Weather-Api...')).toBeInTheDocument();
});