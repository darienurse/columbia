#!/bin/bash

echo "Generating Git commit log..."
git log --reverse --pretty=format:"%an & %ad & %s
" --date=short > gitlog1.md
echo "\begin{center}
\begin{longtabu} to \textwidth {|
    X[4,l]|
    X[3,c]|
    X[8,l]|}
    \hline
    \textbf{Author} & \textbf{Date} & \textbf{Message} \\ \hline" > gitlog.tex

awk '{printf "|%s\n",$0}' < gitlog1.md >> gitlog2.md
pandoc gitlog2.md -o gitlog1.tex
tr '\n' ' ' < gitlog1.tex > gitlog2.tex
sed -i -e 's/\\textbar{}/\'$'\n/g' gitlog2.tex
sed '/^ $/d' gitlog2.tex > gitlog3.tex
sed '/^$/d' gitlog3.tex > gitlog2.tex
awk '{printf "%s\\\\ \\hline\n",$0}' < gitlog2.tex >> gitlog2
sed -i -e 's/\\&/\&/g' gitlog2
cat gitlog2 >> gitlog.tex
echo "\end{longtabu}
\end{center}" >> gitlog.tex
rm gitlog1* gitlog2* gitlog3*

echo "Converting Markdown to TeX..."
SECTIONS="./sections/*.md"

for f in $SECTIONS
do
    echo "Processing $f"
    pandoc $f -o ${f%.md}.tex
	sed -i -e 's/\includegraphics{/\includegraphics[width=\\\textwidth]{/g' ${f%.md}.tex
done

echo "Producing PDF..."
pdflatex paper.tex
pdflatex paper.tex

echo "Cleaning up..."
rm -f *.aux
rm -f *.log
rm -f *.toc
rm ./sections/*.tex
rm ./sections/*.tex-e
