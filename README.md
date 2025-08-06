# NF Innova GitHub API Application

This is a simple Android application that demonstrates consuming the GitHub REST API using modern Android development tools. The app allows users to view a list of repositories for the "octocat" user and view detailed information about a selected repository, including its tags.

---

## Features

- Display GitHub repositories for a "octocat" user
- View detailed repository information (name, description, photo, forks, watchers, and tags)
- Clean MVVM architecture
- Repository pattern for scalable data flow
- Unit and UI testing included
- GitHub Action trigger on push checks unit tests

---

## Future Enhancements

- Adding user search input field
- Adding local caching
- Adding pagination

---

## Project Structure

The project follows a **feature-based package structure** within a single `app` module, designed to support easy migration to a multi-module setup in the future.
