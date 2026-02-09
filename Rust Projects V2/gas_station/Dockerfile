# Stage 1: build
FROM rust:1.89-bullseye AS builder

WORKDIR /usr/src/app

# Copy manifest and fetch dependencies (for caching)
COPY Cargo.toml ./
COPY Rocket.toml ./
COPY certs/ ./
# Copy source
COPY log ./log
COPY src ./src
RUN cargo fetch



# Build release
RUN cargo build --release

# Stage 2: minimal runtime
FROM debian:bookworm-slim

# Install OpenSSL for TLS
RUN apt-get update && apt-get install -y ca-certificates libssl-dev && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy binary from builder
COPY --from=builder /usr/src/app/target/release/gas_station ./

COPY Rocket.toml ./Rocket.toml
COPY certs/ ./certs/

## Set environment variables (optional, can also use .env)
ENV ROCKET_PORT=8080
ENV ROCKET_ADDRESS=0.0.0.0
ENV ROCKET_TLS_CERTS=certs/cert.pem
ENV ROCKET_TLS_KEY=certs/key.pem

EXPOSE 8080

# Set entrypoint
CMD ["./gas_station"]
