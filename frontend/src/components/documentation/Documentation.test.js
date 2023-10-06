import { render, screen } from "@testing-library/react";
import Documentation from "./Documentation";

it("renders documentation component", () => {
    render(<Documentation />);
    expect(screen.getByText('Documentation')).toBeInTheDocument();
});