/*
 * Copyright 2015 Johannes Donath <johannesd@torchmind.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.torchmind.candle.mapper.annotation;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

/**
 * Provides an annotation for defining comments for documentation purposes on types or properties.
 * <strong>Note:</strong> Comments defined by this annotation will be generated <strong>only</strong> when generating a
 * <strong>new configuration</strong> (e.g. when no user-configuration) is present. Otherwise the old node structure is
 * preserved as far as possible (unknown properties will be ignored and thus removed).
 * @author Johannes Donath
 */
@Documented
@Retention (RetentionPolicy.RUNTIME)
@Target ({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
public @interface Comment {

        /**
         * Defines the comment content.
         * @return The content.
         */
        @Nonnull
        String value ();
}
