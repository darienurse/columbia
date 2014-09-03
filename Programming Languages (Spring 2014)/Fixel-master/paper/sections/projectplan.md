# Project Plan
Throughout the development of our language, Fixel has continued to evolve.  During this process, our team has used various tools to aid in organization and consistency.   Continuous reganization and communication has proved invaluable as we have worked to create, maintain, and meet the requirements of our development plan.

## Initial Concept Development
Our group began developing project ideas at the very beginning of February.  We began this process by all brainstorming various ideas on a Google Doc.  In this way we ensured that everyone had the opportunity to contribute and enough time to explore possible areas of interest.  Following online discussion we convened for our first team meeting on February 3rd to narrow down our options.  We quickly came to consensus  and decided on an image processing language.  Due to the widespread sharing of images on social media tools such as Instagram and Facebook, it became clear that we wanted to include features in our language that tied together elements and conventions found in these products.

## Project Management Tools
It was clear that, due to the large nature of the project, as well as the fact that the entire team would be contributing over the course of the semester, it would not be trivial to keep everyone up to date.  However, we quickly realized that maintaining strong communication was integral to the success of the project. As a result, we worked early on to create an environment that made collaboration and communication easy.  The primary tools we used in this effort were email, Google Docs, and Git hosted on BitBucket.  Email was used for updates as well as questions in between our weekly meetings while Git was used for version control.  We utilized Google Docs extensively for many purposes including, but not limited to, collaborating on the written deliverables and tracking the decisions made at meetings.  Finally, the meeting tool When2Meet ended up being very useful in order to determine meeting times.

## Planning
Beginning on February 3rd, we met as a team every Monday evening.  This provided us with an opportunity to discuss the work we had accomplished in the previous week, raise any issues or quesitons we had found regarding Fixel, and assign work for the coming week.  In addition to this meeting, we would often meet during the week in smaller groups, since we typically assigned tasks to multiple people.  These groups were determined based on coinciding availability in the coming week and the times of these meetings were decided upon by the individuals in the subgroup.  Throughout this process we relied on the feedback we received from our Language Tutorial and Language Reference Manual.  Additionally, after implementing a large portion of the decided upon functionality we met with Professor Aho as well as our TA, Ming-Ying Chung.  These meetings helped to shape the final weeks of our project as well as realize some new directions we could take with our language.

## Development
Throughout the development of Fixel, we followed the necessary levels of compiler design.  Having created our repository on Bitbucket we were able to develop on separate branches when necessary and then merge back into the master branch. In order to properly facilitate informed development, we aimed to keep commit messages as descriptive as possible.

We initially implemented a skeleton of the lexer and the parser separately, and once this had been accomplished we proceeded to continue development in parallel based on the development goals set at our weekly meeting.  Once it was possible to link the two units together, we began to implement the code generator.  We also developed the necessary built-in functions in Python simultaneously, and linked those together once code generation was complete.  We continued to develop and update all of the portions until we were satisfied that our language met the design goals.

## Testing
In order to ensure that throughout the development process the new code added did not introduce new bugs to our translator, we unit tested each individual stage.  These tests were mainly responsible for checking that the output at each step, as well as the final output, correctly corresponded to that section's input.  The testing plan is covered in more detail in chapter 8.

## Responsibilities
In order to ensure that collaboration was not only fair, but also as efficient as possible, we decided on all weekly coding responsibilities during our weekly meetings.  These were detailed on a Google Document associated with that week's meeting.  This document provided everyone with a reminder of the information covered during that particular session as well as kept track of what contribution was expected.  We worked to ensure that everyone got to participate in the development of each level of the compiler as well as testing.  In order to maintain consistency we made sure that one of the people in the group for each section had worked on it the previous week or had experience in that particular section.  Each team member's primary areas of work will be discussed in more detail in chapter 6.  In addition, everyone was in charge of a particular portion of the devlopment at a high level as follows:

Project Manager - Kavita Jain-Cocks

Language and Tools Guru - Amrita Mazumdar

System Architect - Darien Nurse

System Integrator: Nilkanth Patel

Tester and Validator - Matthew Patey

## Timeline
The following is the timeline we followed for the development of Fixel:

February 3, 2014....Decide on language idea

March 3, 2014....Determine basic language syntax

March 24, 2014....Create full grammar

April 7, 2014....Generate single line programs in python

April 14, 2014....Create Skeleton of lexer and parser

April 21, 2014....Complete backend python code

April 28, 2014....Generate multiline programs in python

May 5, 2014....Turn fixel source code into executable python

May 12, 2014....Additional functionality, complete Presentation and Final Report
