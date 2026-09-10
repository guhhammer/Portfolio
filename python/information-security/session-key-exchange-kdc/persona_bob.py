from random import randint    # random function import

# Gustavo Hammerschmidt

"""  persona_bob -> Bob's profile  """

# Random keys are used to produce different cryptographic combinations.
# To set a specific key, use the commented code on the right.
K_bob = randint(0, 224)  # int(input("Code number(from 0 to 224): "))   # 0 to 224  # Bob's key    # K_bob = 10


alpha = ['l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'Á', 'é', 'É', 'Í', 'í', 'ó', 'Ó', 'ú', 'Ú', 'à', 'À',
         'è', 'È', 'ì', 'Ì', 'ò', 'Ò', 'ù', 'Ù', 'Â',
         'â', 'Ê', 'ê', 'î', 'Î', 'ô', 'Ô', 'Û', 'û', 'ã', 'Ã',
         'w', 'x', 'y', 'z', '0', '1', 'ë', 'Ë', 'Ï', 'ï', 'Ö', 'ö', 'Ü',
         'ü', 'ñ', 'Ñ', '§', '^', '~', '2', '3', '!', '$', '%', '¨', '&', '*',
         '¬', '£', 'þ',  '´', '`', '<', '>', ';', ':', '|', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
         'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'R', 'T', 'U', 'V', 'W', 'X', 'Й', 'Ц', 'Г', 'Ш', 'Щ', 'З',
         'Ъ', 'Ы', 'Л', 'Д', 'Ж', 'Э', 'Ю', 'Б', 'И', 'Ч', 'Я', 'я', 'ч',
         'и', 'ь', 'б', 'ю', 'э', 'ж', 'д', 'л', 'ы', 'й',
         'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'ц', 'г', 'ш', 'щ', 'з', 'ъ', '-', '_', '=', '/',
         '(', ')', ',',
         '.', 'á', 'õ', 'Õ', 'ä', 'Ä', 'Y', 'Z', 'ç', 'Ç', 'β', 'Γ',
         'γ',
         'Δ', 'δ', 'ε', 'ζ', 'η', 'Θ', 'θ', 'ϑ', '4', '5', '6', '7', '8', '9', '#', '@', 'ι', 'Λ', 'λ', 'μ', 'Ξ',
         'ξ', 'Π', 'π', 'ρ', 'Σ', ' ', 'σ', 'τ', 'Φ', 'φ',
         'ϕ', 'χ', 'Ψ', 'ψ', 'Ω', 'ω', 'ß', 'å', '¢', 'š', 'đ', 'č', 'ć', 'Š', 'Đ', 'Č', 'Ć', 'æ', 'ø', 'Å', 'Æ', 'Ø',
         'ð', 'Þ']     # shuffled alphabet: makes the combinations harder to interpret


# Message from Bob to Alice
m = "Meet me at the library at noon, Alice. Bring the notes!"

# m = str(input("Type a word: "))


def print_persona_bob():
    print("persona_bob: \n"
          "     K_bob - > {}".format(K_bob), "\n\n",
          "     alpha - > {}".format(alpha), "\n\n",
          "     Message without encryption -> {}".format(m), "\n",
          "End: persona_bob\n")

# END
