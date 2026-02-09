FROM debian:bookworm-slim

# Install dependencies
RUN apt update && apt install -y curl build-essential

# Install Rust nightly via rustup
RUN curl https://sh.rustup.rs -sSf | sh -s -- -y --default-toolchain nightly
ENV PATH="/root/.cargo/bin:${PATH}"

WORKDIR /usr/src/app
COPY Cargo.toml Cargo.lock ./
COPY src ./src

RUN cargo build --release
