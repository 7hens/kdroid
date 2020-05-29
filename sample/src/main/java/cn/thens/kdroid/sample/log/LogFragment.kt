package cn.thens.kdroid.sample.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import cn.thens.logdog.LogMessages
import cn.thens.logdog.Logdog

class LogFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        testWithLongNameXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX()
        test()
        val context = container!!.context
        return LinearLayout(context).apply {
            Button(context).apply {
                text = "LOG"
                setOnClickListener {
                    testWithLongNameXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX()
                    test()
                }
            }.let { addView(it) }
        }
    }

    private fun test() {
        Logdog.get().debug(LogMessages.count("logdog"))
        Logdog.get().debug(LogMessages.time("logdog"))
        Logdog.get().debug(TEXT)
        Logdog.get().debug(listOf(TEXT, TEXT, TEXT))
        Logdog.get().warn(TEXT)
        Logdog.get().error(RuntimeException())
        Logdog.get().onlyIf(false).error(TEXT)
        Logdog.get().debug(LogMessages.time("logdog"))
        Logdog.get().debug(LogMessages.count("logdog"))
        Logdog.get().debug(LogMessages.memory(requireContext()))
        Logdog.get().error(Throwable())
        Logdog.get().tag("@TestTag").debug("hello, test tag")
    }

    private fun testWithLongNameXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX() {
        Logdog.get().debug(LogMessages.count("logdog"))
    }

    companion object {
        const val TEXT = "An article (with the linguistic glossing abbreviation art) is a word that is used with a noun (as a standalone word or a prefix or suffix) to specify grammatical definiteness of the noun, and in some languages extending to volume or numerical scope.\n" +
                "\n" +
                "The articles in English grammar are the and a/an, and in certain contexts some. \"An\" and \"a\" are modern forms of the Old English \"an\", which in Anglian dialects was the number \"one\" (compare \"on\" in Saxon dialects) and survived into Modern Scots as the number \"owan\". Both \"on\" (respelled \"one\" by the Norman language) and \"an\" survived into Modern English, with \"one\" used as the number and \"an\" (\"a\", before nouns that begin with a consonant sound) as an indefinite article.\n" +
                "\n" +
                "In many languages, articles are a special part of speech which cannot be easily combined[clarification needed] with other parts of speech. In English grammar, articles are frequently considered part of a broader category called determiners, which contains articles, demonstratives (such as \"this\" and \"that\"), possessive determiners (such as \"my\" and \"his\"), and quantifiers (such as \"all\" and \"few\").[1] Articles and other determiners are also sometimes counted as a type of adjective, since they describe the words that they precede. [2]\n" +
                "\n" +
                "In languages that employ articles, every common noun, with some exceptions, is expressed with a certain definiteness, definite or indefinite, as an attribute (similar to the way many languages express every noun with a certain grammatical number—singular or plural—or a grammatical gender). Articles are among the most common words in many languages; in English, for example, the most frequent word is the.[3]\n" +
                "\n" +
                "Articles are usually categorized as either definite or indefinite.[4] A few languages with well-developed systems of articles may distinguish additional subtypes. Within each type, languages may have various forms of each article, due to conforming to grammatical attributes such as gender, number, or case. Articles may also be modified as influenced by adjacent sounds or words as in elision (e.g., French \"le\" becoming \"l'\" before a vowel), epenthesis (e.g., English \"a\" becoming \"an\" before a vowel), or contraction (e.g. Irish \"i + na\" becoming \"sna\")."
    }
}