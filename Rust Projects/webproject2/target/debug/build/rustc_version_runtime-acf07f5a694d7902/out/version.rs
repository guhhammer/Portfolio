
            /// Returns the `rustc` SemVer version and additional metadata
            /// like the git short hash and build date.
            pub fn version_meta() -> VersionMeta {
                VersionMeta {
                    semver: Version {
                        major: 1,
                        minor: 84,
                        patch: 1,
                        pre: vec![],
                        build: vec![],
                    },
                    host: "x86_64-unknown-linux-gnu".to_owned(),
                    short_version_string: "rustc 1.84.1 (e71f9a9a9 2025-01-27)".to_owned(),
                    commit_hash: Some("e71f9a9a98b0faf423844bf0ba7438f29dc27d58".to_owned()),
                    commit_date: Some("2025-01-27".to_owned()),
                    build_date: None,
                    channel: Channel::Stable,
                }
            }
            