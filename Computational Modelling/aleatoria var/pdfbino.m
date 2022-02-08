function [ ] = pdfbino(n, p, nElm)
    aux = binornd(n,p,1,nElm);
    x = 0:n;
    [y1 x] = hist(aux, x);
    y1 = y1/nElm;
    y2 = binopdf(x,n,p);
    plot(x,y1,'-blue', x,y2,'-red')
end