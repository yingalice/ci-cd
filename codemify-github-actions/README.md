# Github Actions Tutorial

## About
- Course: [Github actions tutorial for QA Engineer | SDET](https://www.youtube.com/watch?v=DrOJ8oTh4Y8)
- Instructor: [Codemify](https://www.youtube.com/@Codemify)
- Code: [Playwright Github Actions Workflow](https://codemify.com/playwright-github-actions-workflow)
- `npm init playwright@latest`
  - Do you want to use TypeScript or JavaScript: `Javascript`
  - Where to put your end-to-end tests? `tests`
  - Add a GitHub Actions workflow? `y`
  - Install Playwright browsers? `Y`

## Relevant files
- See `.github/workflows/playwright.yml` in root folder
- Trigger workflow:
  - Automatically: Push code to GitHub
  - Manually: Click "Actions" tab in GitHub.  Click "Playwright Tests", then "Run workflow"

## Workflow explanation
1. **Triggering Events**
    - The `on` section defines when the workflow should be triggered:
      - `push`: Triggers the workflow when code is pushed to the main or master branches.
      - `pull_request`: Triggers the workflow when a pull request is made to the main or master branches.
      - `workflow_dispatch`: Allows manual triggering of the workflow with specified inputs (ie. browser)

1. **Job Definition**
    - The jobs section defines the individual jobs that will run as part of the workflow:
      - `test`: The name of the job that will run the Playwright tests.

1. **Job Configuration**
    - The test job is configured with the following properties:
      - `timeout-minutes`: Sets a timeout of 60 minutes for the job.
      - `runs-on`: Specifies that the job should run on the latest Ubuntu runner.

1. **Job Steps**
    - The steps section defines the steps that the job will execute:
    - **Checkout the Code:**
      - Checks out repository code so it can be usd in subsequent steps
      ```
      - uses: actions/checkout@v4
      ```
    - **Set Up Node.js:**
      - Using the latest LTS (Long Term Support) version:
      ```
      - uses: actions/setup-node@v4
        with:
          node-version: lts/*
      ```
    - **Install Dependencies:**
      - Installs project dependencies using `npm ci`, which is optimized for CI environments
      ```
      - name: Install dependencies
        run: npm ci
      ```
    - **Install Playwright Browsers:**
      - Installs necessary browsers for Playwright to run tests
      ```
      - name: Install Playwright Browsers
        run: npx playwright install --with-deps
      ```
    - **Run Playwright Tests:**
      - The `--project` flag specifies the browser to use for the tests, which is provided as an input when the workflow is manually triggered
      ```
      - name: Run Playwright tests
        run: npx playwright test --project ${{ github.event.inputs.browser || 'chromium' }}
      ```
    - **Upload Test Artifacts:**
      - Uploads Playwright test report as an artifact.  `${{ !cancelled() }}` condition ensures report is uploaded even if tests fail.  Artifact is retained for 30 days.
      ```
      - uses: actions/upload-artifact@v4
        if: ${{ !cancelled() }}
        with:
          name: playwright-report
          path: playwright-report/
          retention-days: 30
      ```