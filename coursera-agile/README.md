# Coursera: Agile Project Management

## About
- Course: [Agile Project Management](https://www.coursera.org/learn/agile-project-management)
- Instructor: [Google Career Certificates](https://www.coursera.org/instructor/google-career-certificates)

## Agile
- Waterfall
  - Linear ordering of phases, tries to avoid change, completing one phase at a time
  - Requirements --> Analysis --> Design --> Development --> Testing --> Deployment --> Maintenance
- Agile
  - Popular approach/philosophy to delivering project and value to customers
  - Iterative, flexible approach that incorporates change, shorter blocks of time
  - Reduce waste by increasing team/stakeholder collaboration (less documentation, earlier feedback)
- Agile values:
  - Individuals and interactions over processes and tools
  - Working software over comprehensive documentation
  - Customer collaboration over contract negotiation
  - Responding to change over following a plan
  - VUCA (Volatility, Uncertainty, Complexity, Ambiguity) - Projects with high VUCA are good candidates for Agile
- Agile methodologies:
  - Scrum
    - Most popular Agile framework, see next section
  - KanBan:
    - Provides transparent visual feedback on the status of work in progress, and ensures team only accepts a sustainable amount of in-progress work (team sets WIP limit, how much work team can actively works on at a given time)
    - Board displays "To Do", "In Progress", "Done"
  - XP (Extreme Programming):
    - Taking best practices to the extreme
    - XP activities: Designing, coding, testing, listening
    - Innovative practices: Pair programming, continuous integration, avoid big design up front, write tests, not requirements
  - Lean:
    - Eliminate waste and deliver value to customers
    - Define values, map value stream, create flow, establish pull, pursue perfection
## Scrum
- Definition:
  - In Scrum, work is completed in short cycles called Sprints, and the team meets daily to discuss current tasks and their progress.  Ideally 3-9 cross-functional team members
  - Iterative approach: Time-boxed repeating cycles of delivery
  - Incremental approach: Work divded into smaller chunks that build on each other
  - Pillars: Transparency (openness, focus), inspection (courage, respect), adaptation (courage, commitment)
- Product Backlog (project requirements in non-Agile): List of all possible things to work on to achieve project goal.  Prioritized and managed continuously.  Has item description, value to business, relative effort estimate, order priority
  - Backlog refinement keeps living document up to date with descriptions, estimates, and priority
  - Relative estimation methods: T-shirt sizes, story points (Fibonacci)
  - User stories: Short feature description from user's perpsective.  Includes user, action, benefit.  "As a \<user\>, I want this \<action\>, so I can get this \<value\>".  Definition of Done is the items that must be completed for story to be considered complete.  INVEST (independent, negotiable, valuable, estimable, small, testable)
  - Acceptance criteria: Checklist to determine if story is done
  - Epic ("sections"): A collection of user stories
- Scrum events:
  - Sprint (Iterations): Time-boxed iteration where work is done (1-4 weeks, usually 2)
    - Sprint Planning: Confirm how much capacity is available.  Create Sprint Backlog, which is items from Product Backlog that will be done in this Sprint.
    - Daily Scrum (Stand-Up): <= 15-minute daily meeting to discuss progress.  What you did yesterday, what will you do today, and any blockers.
    - Sprint Review: Product is demonstrated to entire Scrum Team, provide feedback, and determine which items to mark as done.  Should not exceed 4 hours.
      - Sprint goal is to produce a releasable Product Increment.  A Minimum Viable Product (MVP) has just enough features to satisfy early customers and may take several sprints.
    - Sprint Retrospective: Occurs at end of Sprint.  Scrum Team reflects on what went well, what could be improved.
- Roles:
  - Scrum Master ("when") - Ensuring team follows Agile values, and is responsible for when a team will deliver value to its users, improving efficiency, removing impediments, and building it fast.  Supportive leaders and facilitators who can resolve conflict.
  - Product Owner ("what") - Responsible for ensuring team builds the right thing, and prioritizing inventory of work.  Customer-focused, organized, strong communicator.
  - Development Team ("how") - Responsible for how a team will deliver product, by building it right.  Cross-functional, self-organizing, supportive.
- Charts:
  - Burndown chart: Measures time against work remaining/done.  y-axis (story points), x-axis (days), plot line of points remaining (\ shape).
  - Velocity: How many points a team burns down in a sprint on average.  Use at least 3 averages in Sprint Planning to determine how many items to safely add.