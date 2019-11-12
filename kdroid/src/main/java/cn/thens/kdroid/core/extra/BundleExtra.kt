package cn.thens.kdroid.core.extra

import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import androidx.annotation.RequiresApi
import cn.thens.kdroid.core.extra.ExtraProperty.with
import java.io.Serializable
import kotlin.properties.ReadWriteProperty

/**
 * @author 7hens
 */
@Suppress("unused")
object BundleExtra {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun binder(): ReadWriteProperty<Bundle, IBinder?> {
        return with(Bundle::getBinder, Bundle::putBinder)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun boolean(defaultValue: Boolean = false): ReadWriteProperty<Bundle, Boolean> {
        return with(defaultValue, Bundle::getBoolean, Bundle::putBoolean)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun booleanArray(): ReadWriteProperty<Bundle, BooleanArray?> {
        return with(Bundle::getBooleanArray, Bundle::putBooleanArray)
    }

    fun bundle(): ReadWriteProperty<Bundle, Bundle?> {
        return with(Bundle::getBundle, Bundle::putBundle)
    }

    fun byte(defaultValue: Byte): ReadWriteProperty<Bundle, Byte> {
        return with(defaultValue, Bundle::getByte, Bundle::putByte)
    }

    fun byteArray(): ReadWriteProperty<Bundle, ByteArray?> {
        return with(Bundle::getByteArray, Bundle::putByteArray)
    }

    fun char(defaultValue: Char = Char.MIN_VALUE): ReadWriteProperty<Bundle, Char> {
        return with(defaultValue, Bundle::getChar, Bundle::putChar)
    }

    fun charArray(): ReadWriteProperty<Bundle, CharArray?> {
        return with(Bundle::getCharArray, Bundle::putCharArray)
    }

    fun charSequence(defaultValue: CharSequence = ""): ReadWriteProperty<Bundle, CharSequence> {
        return with(defaultValue, Bundle::getCharSequence, Bundle::putCharSequence)
    }

    fun charSequenceArray(): ReadWriteProperty<Bundle, Array<CharSequence>?> {
        return with(Bundle::getCharSequenceArray, Bundle::putCharSequenceArray)
    }

    fun charSequenceArrayList(): ReadWriteProperty<Bundle, java.util.ArrayList<CharSequence>?> {
        return with(Bundle::getCharSequenceArrayList, Bundle::putCharSequenceArrayList)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun double(defaultValue: Double = 0.0): ReadWriteProperty<Bundle, Double> {
        return with(defaultValue, Bundle::getDouble, Bundle::putDouble)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun doubleArray(): ReadWriteProperty<Bundle, DoubleArray?> {
        return with(Bundle::getDoubleArray, Bundle::putDoubleArray)
    }

    fun float(defaultValue: Float = 0F): ReadWriteProperty<Bundle, Float> {
        return with(defaultValue, Bundle::getFloat, Bundle::putFloat)
    }

    fun floatArray(): ReadWriteProperty<Bundle, FloatArray?> {
        return with(Bundle::getFloatArray, Bundle::putFloatArray)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun int(defaultValue: Int = 0): ReadWriteProperty<Bundle, Int> {
        return with(defaultValue, Bundle::getInt, Bundle::putInt)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun intArray(): ReadWriteProperty<Bundle, IntArray?> {
        return with(Bundle::getIntArray, Bundle::putIntArray)
    }

    fun intArrayList(): ReadWriteProperty<Bundle, java.util.ArrayList<Int>?> {
        return with(Bundle::getIntegerArrayList, Bundle::putIntegerArrayList)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun long(defaultValue: Long = 0): ReadWriteProperty<Bundle, Long> {
        return with(defaultValue, Bundle::getLong, Bundle::putLong)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun longArray(): ReadWriteProperty<Bundle, LongArray?> {
        return with(Bundle::getLongArray, Bundle::putLongArray)
    }

    fun <T : Parcelable> parcelable(): ReadWriteProperty<Bundle, T> {
        return with(Bundle::getParcelable, Bundle::putParcelable)
    }

    fun parcelableArray(): ReadWriteProperty<Bundle, Array<Parcelable>?> {
        return with(Bundle::getParcelableArray, Bundle::putParcelableArray)
    }

    fun <T : Parcelable> parcelableArrayList(): ReadWriteProperty<Bundle, ArrayList<T>> {
        return with(Bundle::getParcelableArrayList, Bundle::putParcelableArrayList)
    }

    fun serializable(): ReadWriteProperty<Bundle, Serializable?> {
        return with(Bundle::getSerializable, Bundle::putSerializable)
    }

    fun short(defaultValue: Short = 0): ReadWriteProperty<Bundle, Short> {
        return with(defaultValue, Bundle::getShort, Bundle::putShort)
    }

    fun shortArray(): ReadWriteProperty<Bundle, ShortArray?> {
        return with(Bundle::getShortArray, Bundle::putShortArray)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun size(): ReadWriteProperty<Bundle, Size?> {
        return with(Bundle::getSize, Bundle::putSize)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun sizeF(): ReadWriteProperty<Bundle, SizeF?> {
        return with(Bundle::getSizeF, Bundle::putSizeF)
    }

    fun <T : Parcelable> sparseParcelableArray(): ReadWriteProperty<Bundle, SparseArray<T>> {
        return with(Bundle::getSparseParcelableArray, Bundle::putSparseParcelableArray)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun string(defaultValue: String = ""): ReadWriteProperty<Bundle, String> {
        return with(defaultValue, Bundle::getString, Bundle::putString)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun stringArray(): ReadWriteProperty<Bundle, Array<String>?> {
        return with(Bundle::getStringArray, Bundle::putStringArray)
    }

    fun stringArrayList(): ReadWriteProperty<Bundle, java.util.ArrayList<String>?> {
        return with(Bundle::getStringArrayList, Bundle::putStringArrayList)
    }
}