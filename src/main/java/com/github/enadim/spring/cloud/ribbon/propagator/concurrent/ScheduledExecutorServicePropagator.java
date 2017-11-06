/*
 * Copyright (c) 2017 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.enadim.spring.cloud.ribbon.propagator.concurrent;

import com.github.enadim.spring.cloud.ribbon.api.RibbonRuleContext;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * {@link RibbonRuleContext} Propagator over a delegated {@link ScheduledExecutorService}.
 * <p>Copies current {@link RibbonRuleContext} to executor tasks.
 *
 * @author Nadim Benabdenbi
 */
public class ScheduledExecutorServicePropagator extends ExecutorServicePropagator implements ScheduledExecutorService {

    /**
     * the delegate executor.
     */
    private final ScheduledExecutorService delegate;

    /**
     * Sole Constructor.
     *
     * @param delegate the delegate executor.
     */
    public ScheduledExecutorServicePropagator(@NotNull ScheduledExecutorService delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return delegate.schedule(PropagationRunnable.wrap(command), delay, unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return delegate.schedule(PropagationCallable.wrap(callable), delay, unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return delegate.scheduleAtFixedRate(PropagationRunnable.wrap(command), initialDelay, period, unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return delegate.scheduleWithFixedDelay(PropagationRunnable.wrap(command), initialDelay, delay, unit);
    }
}
