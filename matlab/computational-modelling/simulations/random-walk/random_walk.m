%% Random walk: probability of ending at position k after n steps
%
%  Each step moves -1 or +1 with equal probability.  The script asks for
%  the walk length n, the final position k and the number of simulations,
%  then prints the exact probability (random_walk_theoretical) next to
%  the simulated one (random_walk_simulated).

clear; close all; clc

n = input('Walk length (n): ');
k = input('Final position (k): ');

prob_theoretical = random_walk_theoretical(n, k);

fprintf('Walk length (n): %d\n', n);
fprintf('Final position (k): %d\n', k);
fprintf('Probability from the closed formula: %f\n\n', prob_theoretical);

n_sim = input('Number of simulations: ');
fprintf('\n');

prob_simulated = random_walk_simulated(n, k, n_sim);

fprintf('Walk length (n): %d\n', n);
fprintf('Final position (k): %d\n', k);
fprintf('Number of simulations: %d\n', n_sim);
fprintf('Probability from simulation: %f\n', prob_simulated);
