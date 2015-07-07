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
parser grammar CandleParser;

options {
        tokenVocab = CandleLexer;
}

// Entrypoint Rule
candle: expression*;
expression: comment | assignment;
assignment: object | property;

// Comments
comment: commentMultiline | commentSingleline;
commentMultiline: COMMENT;
commentSingleline: COMMENT_LINE;

// Objects & Properties
object: objectIdentifier BRACE_OPEN expression* BRACE_CLOSE;
objectIdentifier: IDENTIFIER;

property: propertyIdentifier EQUALS propertyValue;
propertyIdentifier: IDENTIFIER;

// Property Value Types
propertyValue: propertyValueArray | propertyValueBoolean | propertyValueEnum | propertyValueFloat | propertyValueInteger | propertyValueNull | propertyValueString;
propertyValueBoolean: TRUE | FALSE;
propertyValueEnum: IDENTIFIER;
propertyValueFloat: NUMBER_FLOAT;
propertyValueInteger: NUMBER_INTEGER;
propertyValueNull: NULL;
propertyValueString: STRING;

propertyValueArray: BRACKET_OPEN propertyValueArrayElementList? BRACKET_CLOSE;
propertyValueArrayElementList: propertyValueArrayElement (COMMA propertyValueArrayElementList?)?;
propertyValueArrayElement: propertyValueBoolean | propertyValueEnum | propertyValueFloat | propertyValueInteger | propertyValueNull | propertyValueString;
