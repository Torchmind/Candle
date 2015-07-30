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
 * Provides an annotation for defining methods for gracefully migrating properties of previous application versions into
 * a new format.
 * @author Johannes Donath
 */
@Documented
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface Migrate {

        /**
         * Defines the property name to inject.
         * @return The property name or path.
         */
        @Nonnull
        String value ();

        /**
         * Defines whether users may use the {@code default}-keyword.
         * @return If {@code true}, the {@code default}-keyword is a valid value for this property.
         */
        boolean allowDefault () default true;

        /**
         * Defines whether users may set the property value to {@code null}.
         * @return If {@code true}, {@code null} is a valid value for this property.
         */
        boolean nullable () default true;

        /**
         * Defines whether the value may be omitted in existing configuration files.
         * @return True if
         */
        boolean required () default false;
}
