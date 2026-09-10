function result = factorial_loop(n)
%FACTORIAL_LOOP n! computed with a countdown loop.
%   Written as a first exercise on functions and loops; the built-in
%   factorial(n) does the same job.

  result = 1;
  for k = n:-1:1
    result = result * k;
  end
end
