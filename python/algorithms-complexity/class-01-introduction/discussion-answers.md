# Class 1: discussion answers (translated)

**1) Would you have any reason to study algorithms?** Only one, in my first view: a competition between companies or teams to deliver a result first. In general there would be no goal as long as the algorithm was logically correct. (The course changed this view, see question 3.)

**2) Given the concrete case described, discuss it and write a reflection.** Even with more resources, one computer can perform better than another depending on the efficiency of the algorithm; the best possible scenario depends on the storage and processing capacity of the machine and on the logical efficiency of the algorithm.

**3) Sorting an array of ten million numbers:**
- a) insertion sort: (2 * 10,000,000^2) / 1,000,000,000 = 200,000 seconds.
- b) merge sort: (50 * 10,000,000 * log2 10,000,000) / 10,000,000 = 1,162.67 seconds.
- c) The slower computer got the better result once n became large: about 171 times faster. The larger the input, the more the complexity of the algorithm matters compared with the speed of the machine.

**4) Are algorithms really that important compared with fast hardware, GUIs, networks, object-oriented systems and cloud computing?** Yes. If resources tended to the best possible quality (maximum processing power, free memory, ...), a random algorithm could get close to the result of an efficient one in a short time. But resources are finite, so an efficient algorithm can offset the cost of more advanced hardware by using better techniques and lower complexity.
