# Docker and DevOps

How the services in this portfolio are containerized and operated, in one place. The Dockerfiles live next to the code they build (a container image is part of each project), so this folder is an index plus the reusable notes and a minimal example.

For a non-technical reader: Docker packages a program together with everything it needs so it runs the same way on any machine or cloud. DevOps is the practice of building, shipping and monitoring software that way.

## Container images in this repository

| Project | Files | What the image does |
| --- | --- | --- |
| [`rust/gas-station-api/`](../rust/gas-station-api/) | `Dockerfile`, `.dockerignore`, `mongodb-docker-controller.sh` | Two-stage build (Rust builder, `debian:bookworm-slim` runtime) of the Rocket REST API with TLS; a helper script starts a MongoDB container with a persistent volume. |
| [`rust/rocket-mongodb-web-app/`](../rust/rocket-mongodb-web-app/) | `Dockerfile`, `docker-compose.yml`, `docker.sh` | Two-stage Rust build plus a Compose stack with a MongoDB service, health check and dependency ordering; `docker.sh` wraps build, restart, logs, prune and shell access. |
| [`rust/distributed-experiments/lan-peer-communication/`](../rust/distributed-experiments/lan-peer-communication/) | `Dockerfile` | Builds the LAN peer so several containers can discover each other on a Docker network. |
| [`python-docker-hello/`](python-docker-hello/) | `Dockerfile`, `app.py` | The smallest possible image (`python:3.12-slim`) to check a Docker installation. |

Deployment stack used for the live sites listed in [`../rust/external-projects/live-sites.txt`](../rust/external-projects/live-sites.txt): Docker, Nginx, Cloudflare, Linux, GitHub Actions, PostgreSQL and MongoDB.

## Notes

- [`notes/docker-cheat-sheet.md`](notes/docker-cheat-sheet.md): build, run, exec, logs and cleanup commands for a service image.
- [`notes/docker-daemon-and-cleanup.md`](notes/docker-daemon-and-cleanup.md): managing the Docker service on a Linux host and pruning disk space.
- [`../rust/notes/mongodb-cheat-sheet.md`](../rust/notes/mongodb-cheat-sheet.md): the database the containers above talk to.

## Certificates

The IBM DevOps and Software Engineering professional track (introduction to DevOps, cloud computing, containers, microservices and serverless, CI/CD, monitoring and observability, DevSecOps, test- and behavior-driven development) and Duke University's "Rust for DevOps" are in [`../certificates/courses/`](../certificates/courses/).

## Try it

```bash
cd docker-devops/python-docker-hello
docker build -t python-docker-hello .
docker run --rm python-docker-hello
```
