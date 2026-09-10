% Training set for the naive Bayes classifier ("does the customer buy a
% computer?").  One observation per row of X, one label per row of Y.
%
%   column 1  age          1 = under 30, 2 = 30 to 40, 3 = over 40
%   column 2  income       1 = low, 2 = medium, 3 = high
%   column 3  student      1 = yes, 2 = no
%   column 4  credit       1 = fair, 2 = excellent
%   Y         buys         1 = yes, 2 = no
%
% Run this script (>> training_data) to load X and Y into the workspace.

X = [
    1 3 2 1;
    1 3 2 1;
    2 3 2 1;
    3 2 2 1;
    3 1 1 1;
    3 1 1 2;
    2 1 1 2;
    1 2 2 1;
    1 1 1 1;
    3 2 1 1;
    1 2 1 2;
    2 2 2 2;
    2 3 1 1;
    3 2 2 2
    ];

Y = [
    2;
    2;
    1;
    1;
    1;
    2;
    1;
    2;
    1;
    1;
    1;
    1;
    1;
    2
    ];
