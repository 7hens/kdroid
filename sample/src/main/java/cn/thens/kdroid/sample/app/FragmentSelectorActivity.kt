package cn.thens.kdroid.sample.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cn.thens.kdroid.app.FragmentSelector
import cn.thens.kdroid.util.Logdog
import cn.thens.kdroid.sample.R
import kotlinx.android.synthetic.main.activity_fragment_selector.*

class FragmentSelectorActivity : AppCompatActivity() {
    private val fragmentSelector by lazy {
        FragmentSelector.create(supportFragmentManager, R.id.vFragmentContainer,
                listOf(FragmentOne::class.java, FragmentTwo::class.java, FragmentThree::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_selector)
        vGoBack.setOnClickListener { finish() }
        vPrev.setOnClickListener { selectFragment(fragmentSelector.currentPosition - 1) }
        vNext.setOnClickListener { selectFragment(fragmentSelector.currentPosition + 1) }
        vLaunch.setOnClickListener { startActivity(Intent(this, FragmentSelectorActivity::class.java)) }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        selectFragment(0)
    }


    private fun selectFragment(position: Int) {
        val size = fragmentSelector.getFragmentSize()
        fragmentSelector.select((position % size + size) % size)
    }

    open class BaseFragment : Fragment() {
        private val name get() = javaClass.simpleName

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            Logdog.debug("$name.onCreateView()")
            return TextView(activity!!).apply {
                text = name
            }
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            Logdog.debug("$name.onViewCreated()")
            super.onViewCreated(view, savedInstanceState)
        }

        override fun onResume() {
            Logdog.debug("$name.onResume()")
            super.onResume()
        }

        override fun setUserVisibleHint(isVisibleToUser: Boolean) {
            Logdog.debug("$name.setUserVisibleHint($isVisibleToUser)")
            super.setUserVisibleHint(isVisibleToUser)
        }

        override fun onHiddenChanged(hidden: Boolean) {
            Logdog.debug("$name.onHiddenChanged($hidden)")
            super.onHiddenChanged(hidden)
        }
    }

    class FragmentOne : BaseFragment()
    class FragmentTwo : BaseFragment()
    class FragmentThree : BaseFragment()
}