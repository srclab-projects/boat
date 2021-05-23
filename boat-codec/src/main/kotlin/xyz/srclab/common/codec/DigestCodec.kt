package xyz.srclab.common.codec

import xyz.srclab.common.codec.CodecAlgorithm.Companion.toCodecAlgorithm
import xyz.srclab.common.lang.toBytes
import xyz.srclab.common.lang.toChars
import java.io.OutputStream
import java.security.MessageDigest

/**
 * Digest codec.
 *
 * @author sunqian
 */
interface DigestCodec : Codec {

    @JvmDefault
    fun digest(data: ByteArray): ByteArray {
        return digest(data, 0)
    }

    @JvmDefault
    fun digest(data: ByteArray, offset: Int): ByteArray {
        return digest(data, offset, data.size - offset)
    }

    @JvmDefault
    fun digest(data: ByteArray, offset: Int, length: Int): ByteArray

    @JvmDefault
    fun digest(data: ByteArray, output: OutputStream): Int {
        return digest(data, 0, output)
    }

    @JvmDefault
    fun digest(data: ByteArray, offset: Int, output: OutputStream): Int {
        return digest(data, offset, data.size - offset, output)
    }

    @JvmDefault
    fun digest(data: ByteArray, offset: Int, length: Int, output: OutputStream): Int {
        val bytes = digest(data, offset, length)
        output.write(bytes)
        return bytes.size
    }

    @JvmDefault
    fun digest(data: CharSequence): ByteArray {
        return digest(data.toBytes())
    }

    @JvmDefault
    fun digest(data: CharSequence, output: OutputStream): Int {
        return digest(data.toBytes(), output)
    }

    @JvmDefault
    fun digestToString(data: ByteArray): String {
        return digest(data).toChars()
    }

    @JvmDefault
    fun digestToString(data: ByteArray, offset: Int): String {
        return digest(data, offset).toChars()
    }

    @JvmDefault
    fun digestToString(data: ByteArray, offset: Int, length: Int): String {
        return digest(data, offset, length).toChars()
    }

    @JvmDefault
    fun digestToString(data: CharSequence): String {
        return digest(data).toChars()
    }

    companion object {

        @JvmName("withAlgorithm")
        @JvmOverloads
        @JvmStatic
        fun CharSequence.toDigestCodec(
            digest: () -> MessageDigest = { MessageDigest.getInstance(this.toString()) }
        ): DigestCodec {
            return this.toCodecAlgorithm().toDigestCodec(digest)
        }

        @JvmName("withAlgorithm")
        @JvmOverloads
        @JvmStatic
        fun CodecAlgorithm.toDigestCodec(
            digest: () -> MessageDigest = { MessageDigest.getInstance(this.name) }
        ): DigestCodec {
            return DigestCodecImpl(this.name, digest)
        }

        @JvmStatic
        fun md2(): DigestCodec {
            return CodecAlgorithm.MD2.toDigestCodec()
        }

        @JvmStatic
        fun md5(): DigestCodec {
            return CodecAlgorithm.MD5.toDigestCodec()
        }

        @JvmStatic
        fun sha1(): DigestCodec {
            return CodecAlgorithm.SHA1.toDigestCodec()
        }

        @JvmStatic
        fun sha256(): DigestCodec {
            return CodecAlgorithm.SHA256.toDigestCodec()
        }

        @JvmStatic
        fun sha384(): DigestCodec {
            return CodecAlgorithm.SHA384.toDigestCodec()
        }

        @JvmStatic
        fun sha512(): DigestCodec {
            return CodecAlgorithm.SHA512.toDigestCodec()
        }

        private class DigestCodecImpl(
            private val algorithm: String,
            private val digest: () -> MessageDigest
        ) : DigestCodec {

            override val name = algorithm

            override fun digest(data: ByteArray, offset: Int, length: Int): ByteArray {
                val digest = this.digest()
                digest.update(data, offset, length)
                return digest.digest()
            }
        }
    }
}