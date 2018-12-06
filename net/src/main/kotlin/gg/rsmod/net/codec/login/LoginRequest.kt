package gg.rsmod.net.codec.login

import io.netty.channel.Channel

/**
 * @author Tom <rspsmods@gmail.com>
 */
data class LoginRequest(val channel: Channel, val username: String, val password: String,
                        val revision: Int, val isaacSeed: IntArray, val crcs: IntArray,
                        val resizableClient: Boolean, val auth: Int, val uuid: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginRequest

        if (channel != other.channel) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (revision != other.revision) return false
        if (!isaacSeed.contentEquals(other.isaacSeed)) return false
        if (!crcs.contentEquals(other.crcs)) return false
        if (resizableClient != other.resizableClient) return false
        if (auth != other.auth) return false
        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = channel.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + revision
        result = 31 * result + isaacSeed.contentHashCode()
        result = 31 * result + crcs.contentHashCode()
        result = 31 * result + resizableClient.hashCode()
        result = 31 * result + auth
        result = 31 * result + uuid.hashCode()
        return result
    }
}