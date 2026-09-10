# Use the official Rust image as a base
FROM rust:latest AS builder

# Set the working directory
WORKDIR /app

# Copy Cargo files first to leverage Docker cache
COPY Cargo.toml Cargo.lock ./
COPY src ./src

# Build the Rocket app in release mode
RUN cargo build --release

# Use a compatible Debian base image with newer glibc
FROM debian:bookworm-slim

# Set the working directory
WORKDIR /app

# Copy the compiled binary from the builder stage
COPY --from=builder /app/target/release/rocket_mongodb_web_app /app/rocket_mongodb_web_app

# Expose the port Rocket uses
EXPOSE 8000

# Run the application
CMD ["/app/rocket_mongodb_web_app"]
