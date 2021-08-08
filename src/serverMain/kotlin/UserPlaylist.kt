data class Playlists(
    val href: String,
    val items: List<Playlist>,
)

data class Playlist(
    val href: String,
    val id: String,
    val name: String,
    val owner: Owner,
    val public: Boolean,
    val snapshotId: String,
    val tracks: List<Tracks>,
    val type: String,
    val uri: String,
)

data class ExternalUrls(
    val spotify: String,
)

data class Owner(
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val type: String,
    val uri: String,
)

data class Tracks(
    val href: String,
    val total: Int
)