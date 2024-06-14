

package ai.inno.clever.service.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class KeyedMutexes<K> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(KeyedMutexes.class);

    private final ConcurrentMap<K, LockContainer> key2Mutex = new ConcurrentHashMap<>();

    private static int REAPER_TIME_OUT_SECONDS = 60 * 60;

    public static AtomicInteger REAPER_SUCCESS_COUNT = new AtomicInteger(0);

    public void lock(K key) {
        LockContainer ourLock = new LockContainer();
        LockContainer theirLock = null;
        while ((theirLock = key2Mutex.putIfAbsent(key, ourLock)) == null) {

        }


        if (ourLock != theirLock) {
             //System.out.println("Old Lock");
            LOGGER.debug("Existing lock used for: {}", key);
        } else {
            LOGGER.debug("New lock for: {}", key);
            //System.out.println("New Lock");
        }

        theirLock.lock.lock();

        theirLock.startDeadlockDetector();

    }

    public void unlock(K key) {

        LockContainer ourLock = key2Mutex.get(key);
        if (ourLock != null) {
            ourLock.stopDeadlockDetector();
        }

        if (ourLock != null &&  ourLock.lock != null && ourLock.lock.isHeldByCurrentThread()) {
            LOGGER.debug("Release lock for: {}", key);
            ourLock.lock.unlock();
            if (!ourLock.lock.hasQueuedThreads()) {
                LOGGER.debug("Removing lock for: {}", key);
                key2Mutex.remove(key);
            }
        }

    }

    public boolean isLocked(K key) {
        LockContainer ourLock = key2Mutex.get(key);
        boolean result = ourLock != null && !ourLock.lock.isHeldByCurrentThread();

        //LOGGER.info("Is locked for: {}? {}", key, result);
        // //System.out.println(Thread.currentThread().getName() +": Is locked:" + result);
        return result;
    }

    private static class LockContainer {

        ReentrantLock lock;
        Thread owner;
        Timer reaper;

        LockContainer() {

            lock = new ReentrantLock(true);

        }

        void startDeadlockDetector() {
            if (owner != null && owner.getName().equalsIgnoreCase( Thread.currentThread().getName())){
                return;
            }

            stopDeadlockDetector();

            owner = Thread.currentThread();
            //System.out.println(Thread.currentThread().getName() + ": Start the detector:" + REAPER_TIME_OUT_SECONDS);
            reaper = new Timer();
            reaper.schedule(new TimerTask() {

                @Override public void run() {
                    if (owner != null) {
                        REAPER_SUCCESS_COUNT.incrementAndGet();


                        LOGGER.debug("Deadlock reaper killing thread {}", owner.getName());
                        reaper = null;
                        try {
                            owner.interrupt();
                        } catch (Throwable ex) {
                            LOGGER.warn("Reaper has interrupted this blocked thread.", ex);
                        }
                    }

                }
            }, REAPER_TIME_OUT_SECONDS * 1000);
        }



        void stopDeadlockDetector() {
            if (reaper != null) {
                if (owner != null) {
                    LOGGER.debug("Deadlock reaper stopped for {}", owner.getName());
                } else {
                    return;
                }

                //System.out.println(Thread.currentThread().getName() + ": Stop detector");
                owner = null;
                try {
                    reaper.cancel();
                    reaper.purge();
                } catch (Exception ex) {
                    LOGGER.warn("Can not stop dealock detector.", ex.getMessage());
                    // ex.printStackTrace();
                    //ignore it
                }
                reaper = null;
            }
        }

    }
}

