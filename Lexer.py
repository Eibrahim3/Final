
import re

class Lexer:
    # Regular expressions for different tokens
    NATURAL_LITERAL_REGEX = r'\b[0-9]+\b'
    REAL_LITERAL_REGEX = r'\b[0-9]+\.[0-9]+\b'
    CHAR_LITERAL_REGEX = r'\'(?:\\.|[^\\\'])\''
    STRING_LITERAL_REGEX = r'\"(?:\\.|[^\\\"])*\"'
    BOOL_LITERAL_REGEX = r'\btrue\b|\bfalse\b'
    FOR_KEYWORD_REGEX = r'\bfor\b'
    IF_KEYWORD_REGEX = r'\bif\b'
    ELSE_KEYWORD_REGEX = r'\belse\b'
    WHILE_KEYWORD_REGEX = r'\bwhile\b'
    DO_KEYWORD_REGEX = r'\bdo\b'
    SWITCH_KEYWORD_REGEX = r'\bswitch\b'
    CASE_KEYWORD_REGEX = r'\bcase\b'
    BREAK_KEYWORD_REGEX = r'\bbreak\b'
    CONTINUE_KEYWORD_REGEX = r'\bcontinue\b'
    RETURN_KEYWORD_REGEX = r'\breturn\b'
    STRING_TYPE_REGEX = r'\bString\b'
    NATURAL_TYPE_REGEX = r'\bint\b'
    CHAR_TYPE_REGEX = r'\bchar\b'
    REAL_TYPE_REGEX = r'\bfloat\b|\bdouble\b'
    BOOL_TYPE_REGEX = r'\bboolean\b'
    ADDITION_REGEX = r'\+'
    SUBTRACTION_REGEX = r'-'
    MULTIPLICATION_REGEX = r'\*'
    DIVISION_REGEX = r'/'
    EXPONENTIATION_REGEX = r'\*\*'
    MODULO_REGEX = r'%'
    GREATER_THAN_REGEX = r'>'
    LESS_THAN_REGEX = r'<'
    GREATER_THAN_OR_EQUAL_REGEX = r'>='
    LESS_THAN_OR_EQUAL_REGEX = r'<='
    EQUAL_TO_REGEX = r'=='
    NOT_EQUAL_TO_REGEX = r'!='
    LOGICAL_NOT_REGEX = r'!'
    LOGICAL_AND_REGEX = r'&&'
    LOGICAL_OR_REGEX = r'\|\|'
    LEFT_PARENTHESIS_REGEX = r'\('
    RIGHT_PARENTHESIS_REGEX = r'\)'
    LEFT_BRACE_REGEX = r'\{'
    RIGHT_BRACE_REGEX = r'\}'
    COMMA_REGEX = r','
    SEMICOLON_REGEX = r';'
    IDENTIFIER_REGEX = r'[a-zA-Z_$][a-zA-Z_$0-9]*'

    def __init__(self, input_str: str):
        self.tokens = []
        self.input_str = input_str

    def tokenize(self):
        # Combine all regular expressions for token recognition
        token_regex = '|'.join([self.NATURAL_LITERAL_REGEX,
                                self.REAL_LITERAL_REGEX,
                                self.CHAR_LITERAL_REGEX,
                                self.STRING_LITERAL_REGEX,
                                self.BOOL_LITERAL_REGEX,
                                self.FOR_KEYWORD_REGEX,
                                self.IF_KEYWORD_REGEX,
                                self.ELSE_KEYWORD_REGEX,
                                self.WHILE_KEYWORD_REGEX,
                                self.DO_KEYWORD_REGEX,
                                self.SWITCH_KEYWORD_REGEX,
                                self.CASE_KEYWORD_REGEX,
                                self.BREAK_KEYWORD_REGEX,
                                self.CONTINUE_KEYWORD_REGEX,
                                self.RETURN_KEYWORD_REGEX,
                                self.STRING
    _TYPE_REGEX,
                            self.NATURAL_TYPE_REGEX,
                            self.CHAR_TYPE_REGEX,
                            self.REAL_TYPE_REGEX,
                            self.BOOL_TYPE_REGEX,
                            self.ADDITION_REGEX,
                            self.SUBTRACTION_REGEX,
                            self.MULTIPLICATION_REGEX,
                            self.DIVISION_REGEX,
                            self.EXPONENTIATION_REGEX,
                            self.MODULO_REGEX,
                            self.GREATER_THAN_REGEX,
                            self.LESS_THAN_REGEX,
                            self.GREATER_THAN_OR_EQUAL_REGEX,
                            self.LESS_THAN_OR_EQUAL_REGEX,
                            self.EQUAL_TO_REGEX,
                            self.NOT_EQUAL_TO_REGEX,
                            self.LOGICAL_NOT_REGEX,
                            self.LOGICAL_AND_REGEX,
                            self.LOGICAL_OR_REGEX,
                            self.LEFT_PARENTHESIS_REGEX,
                            self.RIGHT_PARENTHESIS_REGEX,
                            self.LEFT_BRACE_REGEX,
                            self.RIGHT_BRACE_REGEX,
                            self.COMMA_REGEX,
                            self.SEMICOLON_REGEX,
                            self.IDENTIFIER_REGEX])

    # Remove block comments (/* ... */)
    block_comment_regex = r'/\*.*?\*/'
    self.input_str = re.sub(block_comment_regex, '', self.input_str, flags=re.DOTALL)

    # Remove single line comments (// ...)
    single_line_comment_regex = r'//.*?$'
    self.input_str = re.sub(single_line_comment_regex, '', self.input_str, flags=re.MULTILINE)

    # Tokenize the input string
    for match in re.finditer(token_regex, self.input_str):
        lexeme = match.group()
        token_code = self.get_token_code(lexeme)
        token = Token(lexeme, token_code)
    self.tokens.append(token)

def get_token_code(self, lexeme: str) -> int:
    # Check if the lexeme matches any token pattern and return its code
    if re.match(self.NATURAL_LITERAL_REGEX, lexeme):
        return 1
    elif re.match(self.REAL_LITERAL_REGEX, lexeme):
        return 2
    elif re.match(self.CHAR_LITERAL_REGEX, lexeme):
        return 3
    elif re.match(self.STRING_LITERAL_REGEX, lexeme):
        return 4
    elif re.match(self.BOOL_LITERAL_REGEX, lexeme):
        return 5
    elif re.match(self.FOR_KEYWORD_REGEX, lexeme):
        return 6
    elif re.match(self.IF_KEYWORD_REGEX, lexeme):
        return 7
    elif re.match(self.ELSE_KEYWORD_REGEX, lexeme):
        return 8
    elif re.match(self.WHILE_KEYWORD_REGEX, lexeme):
        return 9
    elif re.match(self.DO_KEYWORD_REGEX, lexeme):
        return 10
    elif re.match(self.SWITCH_KEYWORD_REGEX, lexeme):
        return 11
    elif re.match(self.CASE_KEYWORD_REGEX, lexeme):
        return 12
    elif re.match(self.BREAK_KEYWORD_REGEX, lexeme):
        return 13
    elif re.match(self.CONTINUE_KEYWORD_REGEX, lexeme):
        return 14
    elif re.match(self.RETURN_KEYWORD_REGEX, lexeme):
        return 15
    elif re.match(self.STRING_TYPE_REGEX, lexeme):
        return 16
    elif re.match(self.NATURAL_TYPE_REGEX, lexeme):
        return 17
    elif re.match(self.CHAR_TYPE_REGEX, lexeme):
        return 18
    elif re.match(self.REAL_TYPE_REGEX, lexeme):
        return 19
    elif re.match(self.BOOL_TYPE_REGEX, lexeme):
        return 20
    elif re.match(self.ADDITION_REGEX, lexeme):
        return 21
    elif re.match(self.SUBTRACTION_REGEX, lexeme):
        return 21
    elif re.match(self.SUBTRACTION_REGEX, lexeme):
      return 22
    elif re.match(self.MULTIPLICATION_REGEX, lexeme):
      return 23
    elif re.match(self.DIVISION_REGEX, lexeme):
      return 24
    elif re.match(self.EXPONENTIATION_REGEX, lexeme):
     return 25
    elif re.match(self.MODULO_REGEX, lexeme):
      return 26
    elif re.match(self.GREATER_THAN_REGEX, lexeme):
     return 27
    elif re.match(self.LESS_THAN_REGEX, lexeme):
      return 28
    elif re.match(self.GREATER_THAN_OR_EQUAL_REGEX, lexeme):
      return 29
    elif re.match(self.LESS_THAN_OR_EQUAL_REGEX, lexeme):
     return 30
    elif re.match(self.EQUAL_TO_REGEX, lexeme):
     return 31
    elif re.match(self.NOT_EQUAL_TO_REGEX, lexeme):
      return 32
    elif re.match(self.LOGICAL_NOT_REGEX, lexeme):
      return 33
    elif re.match(self.LOGICAL_AND_REGEX, lexeme):
      return 34
    elif re.match(self.LOGICAL_OR_REGEX, lexeme):
      return 35
    elif re.match(self.LEFT_PARENTHESIS_REGEX, lexeme):
      return 36
    elif re.match(self.RIGHT_PARENTHESIS_REGEX, lexeme):
      return 37
    elif re.match(self.LEFT_BRACE_REGEX, lexeme):
      return 38
    elif re.match(self.RIGHT_BRACE_REGEX, lexeme):
      return 39
    elif re.match(self.COMMA_REGEX, lexeme):
      return 40
    elif re.match(self.SEMICOLON_REGEX, lexeme):
     return 41
    elif re.match(self.IDENTIFIER_REGEX, lexeme):
      return 42
    else:
      return -1
class Token:
  def init(self, lexeme: str, code: int):
   self.lexeme = lexeme
   self.code = code
   def __str__(self):
    return f'Token({self.lexeme}, {self.code})'

def __repr__(self):
    return self.__str__()
input_str = '''int main() {
int a = 10;
int b = 20;
if (a > b) {
printf("a is greater than b");
}
else {
printf("b is greater than a");
}
return 0;
}'''

lexer = Lexer(input_str)
lexer.tokenize()
for token in lexer.tokens:
  print(token)

