# Prior Work

- See short blog article on "Mathematical Logic Tools and Games": http://michaelwehar.com/project1.html

- See informal write-up on related independent study at University at Buffalo: http://michaelwehar.com/documents/independent_study.pdf

- See this branch for probabilistic theorem enumeration and tautology benchmarks:
https://github.com/MichaelWehar/Propositional-Theorem-Enumerator/tree/Generating-Benchmarks

# Background

A statement in propositional calculus might be a tautology, a contradiction, or neither.

If it is a tautology, then that means is has a proof.  That is, there is a sequence of applications of inference rules and axioms that leads you to the tautology.

But, is there a smallest proof?  How many theorems have short proofs?

There are many different axiom systems for propositional calculus.  Take a look at some examples here: https://en.wikipedia.org/wiki/List_of_logic_systems

In some systems, there are tens of thousands of distinct tautologies with proofs using only 5 inference applications.  However, other systems only have a few hundred distinct tautologies with proofs using only 5 inference applications.

# What is this project?

We carry out a sort of unification on propositional formulas to create a grammer for generating/enumerating tautologies.

See here for information on the unification algorithm: https://en.wikipedia.org/wiki/Unification_(computer_science)

# Why does this matter?

(1) Determining whether or not a propositional formula is a tautology is a classical coNP-complete problem.

Information on coNP: https://en.wikipedia.org/wiki/Co-NP-complete

The problem of factoring an integer is in coNP.  This suggests that the tautology problem might be harder than factoring.

(2) A probabilistic version of this enumerator was used to create hard instance benchmarks for the tautology problem.

See this branch: https://github.com/MichaelWehar/Propositional-Theorem-Enumerator/tree/Generating-Benchmarks
