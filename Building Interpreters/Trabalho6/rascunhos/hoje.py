
import re


regex = r"[a-z]"

test_str = ("PUCPR/PPGIA\n"
            "Bloco 8 - Parque Tecnológico - 20 andar\n"
            "Rua Imaculada Conceição, 1155 - Prado Velho\n"
            "CEP 80215-901 - Curitiba - PR\n")

matches = re.finditer(regex, test_str, re.MULTILINE)


for matchNum, match in enumerate(matches):
    matchNum = matchNum + 1
    print("Match (matchNum) encontrado em {start}-{end}: {match}".format(matchNum = matchNum,
                                                                         start = match.start(),
                                                                         end = match.end(),
                                                                         match = match.group()))
