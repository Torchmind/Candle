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
lexer grammar CandleLexer;

// Multiline and Single-Line comments (C-Style)
COMMENT: '/*' .*? '*/';
COMMENT_LINE: '//' ~[\r\n]*;

// String Literals & Numbers
STRING_LITERAL_START: '"' -> pushMode (String);

NUMBER_FLOAT: '-'? NUMBER? '.' [0-9]+ NUMBER_EXP?;
NUMBER_INTEGER: '0x' NUMBER_HEX+ | '-'? NUMBER;

fragment NUMBER: '0' | [1-9] [0-9]*;
fragment NUMBER_EXP: [Ee] [+\-]? NUMBER;
fragment NUMBER_HEX: [0-9A-Fa-f];

// Keywords
COPY: 'copy';
FALSE: 'false';
INCLUDE: 'include';
NULL: 'null' | 'NULL';
OBJECT: 'object';
PROPERTY: 'property';
TRUE: 'true';
TRY: 'try';
IDENTIFIER: [A-Za-z] [A-Za-z0-9_]*;

// Special Characters
BRACE_OPEN: '{';
BRACE_CLOSE: '}';
BRACKET_OPEN: '[';
BRACKET_CLOSE: ']';
COLON: ':';
COMMA: ',';
DOT: '.';
EQUALS: '=';
SEMICOLON: ';';

// Newlines and other whitespace characters
WHITESPACE: [ \t\r\n\u000C]+ -> skip;

// String Literal Contents
mode String;
        STRING_LITERAL_QUOTE: '\\"' { setText ("\""); };
        STRING_LITERAL_BACKSLASH: '\\\\' { setText ("\\"); };
        STRING_LITERAL_BACKSPACE: '\\b' { setText ("\b"); };
        STRING_LITERAL_FORMFILL: '\\f' { setText ("\f"); };
        STRING_LITERAL_NEWLINE: '\\n' { setText ("\n"); };
        STRING_LITERAL_CARRIAGE_RETURN: '\\r' { setText ("\r"); };
        STRING_LITERAL_HORIZONTAL_TAB: '\\t' { setText ("\t"); };

        STRING_LITERAL_ESCAPE_UNICODE: '\\u' NUMBER_HEX NUMBER_HEX NUMBER_HEX NUMBER_HEX {
                String code = getText ();
                code = code.substring (2);

                int unicodeNumber = Integer.decode ("0x" + code).intValue ();
                setText (Character.toString (((char) unicodeNumber)));
        };

        STRING_LITERAL_TEXT: ~[\\"];

        STRING_LITERAL_END: '"' -> popMode;
