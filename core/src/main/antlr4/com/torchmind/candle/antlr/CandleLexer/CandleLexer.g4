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

// Quoted string (high priority and thus up here)
STRING: '"' (ESCAPE_SEQUENCE | ~["\\])* '"';

fragment ESCAPE_SEQUENCE: '\\' (["\\/bfnrt] | UNICODE_ESCAPE_SEQUENCE);
fragment UNICODE_ESCAPE_SEQUENCE: 'u' NUMBER_HEX NUMBER_HEX NUMBER_HEX NUMBER_HEX;

// Keywords
fragment NUMBER_HEX_PREFIX: '0x';
COPY: 'copy';
FALSE: 'false';
INCLUDE: 'include';
NULL: 'null' | 'NULL';
OBJECT: 'object';
PROPERTY: 'property';
TRUE: 'true';
TRY: 'try';

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

// Numbers and identifiers
NUMBER: ('-'? (NUMBER_INTEGER | NUMBER_FLOAT)) | NUMBER_HEX_PREFIX NUMBER_HEX;
IDENTIFIER: [A-Za-z] [A-Za-z0-9_]*;

fragment NUMBER_INTEGER: '0' | [1-9] [0-9]*;
fragment NUMBER_FLOAT: NUMBER_INTEGER '.' [0-9]+ NUMBER_EXPRESSION?;
fragment NUMBER_EXPRESSION: [Ee] [+\-]? NUMBER_INTEGER;
fragment NUMBER_HEX: [0-9A-Fa-f]+;

// Newlines and other whitespace characters
WHITESPACE: [ \t\r\n\u000C]+ -> skip;
