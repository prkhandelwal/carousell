# Carousell Marketplace CLI (Java)

Implements the Carousell marketplace CLI.

This project follows clean, modular design principles (SOLID) and is implemented with plain Java.

---

## Environment

* **Java JDK 11+** (e.g., Amazon Corretto, Temurin, OpenJDK)
* **No external libraries** — only Java standard library
* Optional: **Docker** to run without installing Java locally

---

## Build (Local)

Compile all Java files into the `out/` directory:

```bash
sh ./build.sh
```

If you're on Windows and not using Git Bash:

```powershell
./build.sh
```

---

## Run (Local)

```bash
java -cp out com.carousell.marketplace.cli.Application
```

You will see the interactive prompt:

```
#
```

Then you can run commands like:

```
REGISTER user1
CREATE_LISTING user1 'Phone' 'Black' 1000 'Electronics'
GET_TOP_CATEGORY user1
```

---

# 🐳 Docker Support

Optionally, you can run the entire CLI **without installing Java** using Docker.

---

## Build the Docker Image

From project root:

```bash
docker build -t carousell-cli .
```

This:

* Compiles Java source files inside a JDK container
* Produces a small, optimized runtime image containing only the JRE + compiled app

---

## Run the CLI via Docker (Interactive)

```bash
docker run -it carousell-cli
```

You will see:

```
#
```

Now type commands normally.

---

## 📦 Project Structure

```
carousell-java/
├── src/                     # Source files
├── out/                     # Compiled output (generated)
├── build.sh                 # Build script
├── run.sh                   # Run script (optional)
├── Dockerfile               # Docker build file
└── README.md                # Project documentation
```

---
