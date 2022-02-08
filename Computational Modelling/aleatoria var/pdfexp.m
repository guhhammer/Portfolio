function [ ] = pdfexp(mu, nElm)

e=exprnd(mu,1,nElm);
step = (max(e)-min(e))/1000;
r=min(e):step:max(e);
[y1, x] = hist(e,r);
y1 = y1/nElm*(1/step);      %multiplica por 1/step porque foi 
                            %distribuído no histograma a cada step
y2=exppdf(x,mu);
plot(x, y1, '-blue', x, y2, '-red');
end

