package cn.thens.kdroid.extra

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import cn.thens.kdroid.extra.ExtraProperty.with
import java.io.Serializable
import kotlin.properties.ReadWriteProperty

/**
 * @author 7hens
 */
@Suppress("unused")
interface IntentExtra {

    fun boolean(defaultValue: Boolean = false): ReadWriteProperty<Intent, Boolean> {
        return with(defaultValue, Intent::getBooleanExtra) { n, v -> putExtra(n, v) }
    }

    fun booleanArray(): ReadWriteProperty<Intent, BooleanArray> {
        return with(Intent::getBooleanArrayExtra) { n, v -> putExtra(n, v) }
    }

    fun bundle(): ReadWriteProperty<Intent, Bundle> {
        return with(Intent::getBundleExtra) { n, v -> putExtra(n, v) }
    }

    fun byte(defaultValue: Byte = 0): ReadWriteProperty<Intent, Byte> {
        return with(defaultValue, Intent::getByteExtra) { n, v -> putExtra(n, v) }
    }

    fun byteArray(): ReadWriteProperty<Intent, ByteArray> {
        return with(Intent::getByteArrayExtra) { n, v -> putExtra(n, v) }
    }

    fun char(defaultValue: Char = Char.MIN_VALUE): ReadWriteProperty<Intent, Char> {
        return with(defaultValue, Intent::getCharExtra) { n, v -> putExtra(n, v) }
    }

    fun charArray(): ReadWriteProperty<Intent, CharArray> {
        return with(Intent::getCharArrayExtra) { n, v -> putExtra(n, v) }
    }

    fun charSequence(): ReadWriteProperty<Intent, CharSequence> {
        return with(Intent::getCharSequenceExtra) { n, v -> putExtra(n, v) }
    }

    fun charSequenceArray(): ReadWriteProperty<Intent, Array<CharSequence>> {
        return with(Intent::getCharSequenceArrayExtra) { n, v -> putExtra(n, v) }
    }

    fun charSequenceArrayList(): ReadWriteProperty<Intent, java.util.ArrayList<CharSequence>> {
        return with(Intent::getCharSequenceArrayListExtra) { n, v -> putExtra(n, v) }
    }


    fun double(defaultValue: Double = 0.0): ReadWriteProperty<Intent, Double> {
        return with(defaultValue, Intent::getDoubleExtra) { n, v -> putExtra(n, v) }
    }

    fun doubleArray(): ReadWriteProperty<Intent, DoubleArray> {
        return with(Intent::getDoubleArrayExtra) { n, v -> putExtra(n, v) }
    }

    fun float(defaultValue: Float = 0F): ReadWriteProperty<Intent, Float> {
        return with(defaultValue, Intent::getFloatExtra) { n, v -> putExtra(n, v) }
    }

    fun floatArray(): ReadWriteProperty<Intent, FloatArray> {
        return with(Intent::getFloatArrayExtra) { n, v -> putExtra(n, v) }
    }

    fun int(defaultValue: Int = 0): ReadWriteProperty<Intent, Int> {
        return with(defaultValue, Intent::getIntExtra) { n, v -> putExtra(n, v) }
    }

    fun intArray(): ReadWriteProperty<Intent, IntArray> {
        return with(Intent::getIntArrayExtra) { n, v -> putExtra(n, v) }
    }

    fun intArrayList(): ReadWriteProperty<Intent, java.util.ArrayList<Int>> {
        return with(Intent::getIntegerArrayListExtra) { n, v -> putExtra(n, v) }
    }

    fun long(defaultValue: Long = 0): ReadWriteProperty<Intent, Long> {
        return with(defaultValue, Intent::getLongExtra) { n, v -> putExtra(n, v) }
    }

    fun longArray(): ReadWriteProperty<Intent, LongArray> {
        return with(Intent::getLongArrayExtra) { n, v -> putExtra(n, v) }
    }

    fun <T : Parcelable> parcelable(): ReadWriteProperty<Intent, T> {
        return with(Intent::getParcelableExtra) { n, v -> putExtra(n, v) }
    }

    fun parcelableArray(): ReadWriteProperty<Intent, Array<Parcelable>> {
        return with(Intent::getParcelableArrayExtra) { n, v -> putExtra(n, v) }
    }

    fun <T : Parcelable> parcelableArrayList(): ReadWriteProperty<Intent, ArrayList<T>> {
        return with(Intent::getParcelableArrayListExtra) { n, v -> putExtra(n, v) }
    }

    fun serializable(): ReadWriteProperty<Intent, Serializable> {
        return with(Intent::getSerializableExtra) { n, v -> putExtra(n, v) }
    }

    fun short(defaultValue: Short = 0): ReadWriteProperty<Intent, Short> {
        return with(defaultValue, Intent::getShortExtra) { n, v -> putExtra(n, v) }
    }

    fun shortArray(): ReadWriteProperty<Intent, ShortArray> {
        return with(Intent::getShortArrayExtra) { n, v -> putExtra(n, v) }
    }

    fun string(): ReadWriteProperty<Intent, String> {
        return with(Intent::getStringExtra) { n, v -> putExtra(n, v) }
    }

    fun stringArray(): ReadWriteProperty<Intent, Array<String>> {
        return with(Intent::getStringArrayExtra) { n, v -> putExtra(n, v) }
    }

    fun stringArrayList(): ReadWriteProperty<Intent, java.util.ArrayList<String>> {
        return with(Intent::getStringArrayListExtra) { n, v -> putExtra(n, v) }
    }

    companion object : IntentExtra

    open class CompanionOptions<out Options> : IntentExtra {
        private val className: String by lazy { javaClass.name.replace("\$Companion", "") }

        @Suppress("UNCHECKED_CAST")
        private val options: Options by lazy { this as Options }

        fun intent(context: Context, configure: Options.(Intent) -> Unit): Intent {
            val intent = Intent().setComponent(ComponentName(context.packageName, className))
            return apply(intent, configure)
        }

        fun apply(intent: Intent, configure: Options.(Intent) -> Unit): Intent {
            configure(options, intent)
            return intent
        }
    }
}