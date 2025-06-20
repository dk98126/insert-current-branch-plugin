package com.github.dk98126.branchit

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.VcsDataKeys
import git4idea.repo.GitRepositoryManager

class InsertBranchNameMessageAction : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val commitMessageUi = e.getData(VcsDataKeys.COMMIT_WORKFLOW_UI)?.commitMessageUi ?: return
        val currentBranch = getCurrentGitBranch(project) ?: return
        var message = commitMessageUi.text
        message = "$currentBranch $message"
        commitMessageUi.text = message
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }

    private fun getCurrentGitBranch(project: Project): String? {
        return GitRepositoryManager.getInstance(project).repositories.firstOrNull()?.currentBranchName
    }
}
