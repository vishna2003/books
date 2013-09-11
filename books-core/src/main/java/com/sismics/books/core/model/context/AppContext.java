package com.sismics.books.core.model.context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.sismics.books.core.listener.async.BookImportAsyncListener;
import com.sismics.books.core.listener.async.UserAppCreatedAsyncListener;
import com.sismics.books.core.listener.sync.DeadEventListener;
import com.sismics.books.core.service.BookDataService;
import com.sismics.books.core.service.FacebookService;
import com.sismics.util.EnvironmentUtil;

/**
 * Global application context.
 *
 * @author jtremeaux 
 */
public class AppContext {
    /**
     * Singleton instance.
     */
    private static AppContext instance;

    /**
     * Event bus.
     */
    private EventBus eventBus;
    
    /**
     * Generic asynchronous event bus.
     */
    private EventBus asyncEventBus;

    /**
     * Asynchronous event bus for mass imports.
     */
    private EventBus importEventBus;
    
    /**
     * Service to fetch book informations.
     */
    private BookDataService bookDataService;
    
    /**
     * Facebook interaction service.
     */
    private FacebookService facebookService;
    
    /**
     * Asynchronous executors.
     */
    private List<ExecutorService> asyncExecutorList;
    
    /**
     * Private constructor.
     */
    private AppContext() {
        resetEventBus();
        
        bookDataService = new BookDataService();
        bookDataService.startAndWait();
    }
    
    /**
     * (Re)-initializes the event buses.
     */
    private void resetEventBus() {
        eventBus = new EventBus();
        eventBus.register(new DeadEventListener());
        
        asyncExecutorList = new ArrayList<ExecutorService>();
        
        asyncEventBus = newAsyncEventBus();
        
        importEventBus = newAsyncEventBus();
        importEventBus.register(new BookImportAsyncListener());
        importEventBus.register(new UserAppCreatedAsyncListener());
    }

    /**
     * Returns a single instance of the application context.
     * 
     * @return Application context
     */
    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }
    
    /**
     * Wait for termination of all asynchronous events.
     * /!\ Must be used only in unit tests and never a multi-user environment. 
     */
    public void waitForAsync() {
        if (EnvironmentUtil.isUnitTest()) {
            return;
        }
        try {
            for (ExecutorService executor : asyncExecutorList) {
                // Shutdown executor, don't accept any more tasks (can cause error with nested events)
                try {
                    executor.shutdown();
                    executor.awaitTermination(60, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    // NOP
                }
            }
        } finally {
            resetEventBus();
        }
    }

    /**
     * Creates a new asynchronous event bus.
     * 
     * @return Async event bus
     */
    private EventBus newAsyncEventBus() {
        if (EnvironmentUtil.isUnitTest()) {
            return new EventBus();
        } else {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
            asyncExecutorList.add(executor);
            return new AsyncEventBus(executor);
        }
    }

    /**
     * Getter of eventBus.
     *
     * @return eventBus
     */
    public EventBus getEventBus() {
        return eventBus;
    }

    /**
     * Getter of asyncEventBus.
     *
     * @return asyncEventBus
     */
    public EventBus getAsyncEventBus() {
        return asyncEventBus;
    }
    
    /**
     * Getter of importEventBus.
     *
     * @return importEventBus
     */
    public EventBus getImportEventBus() {
        return importEventBus;
    }

    /**
     * Getter of bookDataService.
     * 
     * @return bookDataService
     */
    public BookDataService getBookDataService() {
        return bookDataService;
    }
    
    /**
     * Getter of facebookService.
     * 
     * @return facebookService
     */
    public FacebookService getFacebookService() {
        return facebookService;
    }
}
