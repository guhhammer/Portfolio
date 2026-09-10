%% Birthday problem: probability that two people in a group share a birthday
%
%  Asks for the group size, prints the exact probability
%  (birthday_analytic) and then a Monte Carlo estimate (birthday_simulated)
%  for a chosen number of simulations.

clear; close all; clc

group_size = input('Number of people in the group: ');

prob_analytic = birthday_analytic(group_size);
fprintf('Group size: %d\n', group_size);
fprintf('Probability from the closed formula: %f\n\n', prob_analytic);

n_sim = input('Number of simulations: ');
fprintf('\n');

prob_simulated = birthday_simulated(group_size, n_sim);
fprintf('Group size: %d\n', group_size);
fprintf('Number of simulations: %d\n', n_sim);
fprintf('Probability from simulation: %f\n', prob_simulated);
