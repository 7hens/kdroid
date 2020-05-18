package cn.thens.kdroid.io

@Suppress("unused")
interface Codec<P, C> {
    fun encode(plain: P): C
    fun decode(cipher: C): P

    fun inverted(): Codec<C, P> {
        val self = this
        return object : Codec<C, P> {
            override fun encode(plain: C): P {
                return self.decode(plain)
            }

            override fun decode(cipher: P): C {
                return self.encode(cipher)
            }
        }
    }

    fun <C2> then(codec: Codec<C, C2>): Codec<P, C2> {
        val self = this
        return object : Codec<P, C2> {
            override fun encode(plain: P): C2 {
                return codec.encode(self.encode(plain))
            }

            override fun decode(cipher: C2): P {
                return self.decode(codec.decode(cipher))
            }
        }
    }

    fun <C2> then(encoder: (C) -> C2, decoder: (C2) -> C): Codec<P, C2> {
        return then(create(encoder, decoder))
    }

    companion object {
        fun <P, C> create(encoder: (P) -> C, decoder: (C) -> P): Codec<P, C> {
            return object : Codec<P, C> {
                override fun encode(plain: P): C {
                    return encoder(plain)
                }

                override fun decode(cipher: C): P {
                    return decoder(cipher)
                }
            }
        }
    }
}
