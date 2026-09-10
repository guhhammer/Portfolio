# Mathematical decision models (Python, Excel, LaTeX)

Coursework from the "Mathematical Decision Models" course at PUCPR (2021): formulating business problems as linear programs and solving them with Excel Solver, multi-criteria decision making with AHP (Analytic Hierarchy Process), Monte Carlo simulation, and system dynamics. Team projects with Gustavo Foroutan Raposo, João Felipe Schwab, Matheus Siqueira and Ricardo Tanji.

For a non-technical reader: these are the tools used to choose the best option when there are many constraints (how much to produce in each plant), several conflicting criteria (which content strategy a YouTube channel should follow) or uncertainty (how many new infections to expect, how an investment portfolio may evolve).

| Folder | What it is | Files |
| --- | --- | --- |
| `linear-programming/` | Twenty linear programming formulations (production planning, diet and feed mixes, transportation, cutting stock, workforce, assignment, advertising) written up in English, solved with Excel Solver and analysed in a 39-page report | `motorauto-production-plan.md`, `exercise-list-1-models.md`, `exercise-list-2-models.md`, `exercise-list-2-solver.xlsx`, `exercise-list-2-report.pdf`, `brute_force_attempt.py` |
| `ahp-youtube-channel/` | AHP model to choose the content strategy of a YouTube channel ("La'Tech Tips"): five criteria with sub-criteria, six alternatives, Saaty pairwise comparisons and the final ranking, written as an ASME-style LaTeX paper. `ahp_normalization.py` normalises the comparison matrices, computes the priority vectors and prints the LaTeX table rows | `paper.tex`, `references.bib`, `report.pdf`, `ahp-model.xlsx`, `sub-criteria.md`, `figures/` |
| `monte-carlo-contagion-simulation/` | Monte Carlo simulation estimating new COVID-19 cases if in-person classes resume, built in a spreadsheet and written up as a paper; two helper scripts (inflation-rate API check, Excel formula builder) | `report.pdf`, `figures/`, `inflation_rate_api_check.py`, `excel_sum_formula_helper.py` |
| `system-dynamics-investment-simulation/` | System-dynamics simulation of an investment portfolio by asset class (stocks, ETFs, dividends, appreciation) month by month in Excel, with a 22-page report; `excel_formula_generator.py` generates the scoring formulas of the workbook | `report.pdf`, `investment-simulation.xlsx`, `figures/`, `excel_formula_generator.py` |

Reports and spreadsheets are in Portuguese; the formulations, code and this index are in English. To build the paper: `latexmk -pdf paper.tex` with the ASME `asme2ej` class installed (bibliography with biber). Third-party articles, the professor's exercise lists and tutorials were removed.
