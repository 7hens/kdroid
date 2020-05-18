package cn.thens.kdroid.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

object TopActivity {
    var state = State.INITIALIZED
        private set(value) {
            field = value
        }

    private var topActivityRef = WeakReference<Activity>(null)

    fun optional(): Activity? = topActivityRef.get()

    fun get(): Activity = optional()!!

    private fun set(activity: Activity, state: State) {
        if (state <= State.RESUMED) {
            this.state = state
            topActivityRef = WeakReference(activity)
        } else if (activity == get()) {
            this.state = state
        }
    }

    internal fun initialize(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                set(activity, State.CREATED)
            }

            override fun onActivityStarted(activity: Activity) {
                set(activity, State.STARTED)
            }

            override fun onActivityResumed(activity: Activity) {
                set(activity, State.RESUMED)
            }

            override fun onActivityPaused(activity: Activity) {
                set(activity, State.PAUSED)
            }

            override fun onActivityStopped(activity: Activity) {
                set(activity, State.STOPPED)
            }

            override fun onActivityDestroyed(activity: Activity) {
                set(activity, State.DESTROYED)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) = Unit
        })
    }

    enum class State {
        INITIALIZED,
        CREATED,
        STARTED,
        RESUMED,
        PAUSED,
        STOPPED,
        DESTROYED
    }
}