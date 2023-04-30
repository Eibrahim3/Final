class Compiler:
    def __init__(self):
        self.input_str = ""

    def read_file(self, file_path):
        with open(file_path, 'r') as f:
            self.input_str = f.read()
