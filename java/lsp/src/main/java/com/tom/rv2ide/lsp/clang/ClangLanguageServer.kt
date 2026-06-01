/*
 *  This file is part of AndroidCodeStudio.
 *
 *  AndroidCodeStudio is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidCodeStudio is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidCodeStudio.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.tom.rv2ide.lsp.clang

import android.content.Context
import com.tom.rv2ide.lsp.api.ILanguageClient
import com.tom.rv2ide.lsp.api.ILanguageServer
import com.tom.rv2ide.lsp.api.IServerSettings
import com.tom.rv2ide.lsp.models.CodeFormatResult
import com.tom.rv2ide.lsp.models.CompletionParams
import com.tom.rv2ide.lsp.models.CompletionResult
import com.tom.rv2ide.lsp.models.DefinitionParams
import com.tom.rv2ide.lsp.models.DefinitionResult
import com.tom.rv2ide.lsp.models.DiagnosticResult
import com.tom.rv2ide.lsp.models.ExpandSelectionParams
import com.tom.rv2ide.lsp.models.FormatCodeParams
import com.tom.rv2ide.lsp.models.LSPFailure
import com.tom.rv2ide.lsp.models.MarkupContent
import com.tom.rv2ide.lsp.models.ReferenceParams
import com.tom.rv2ide.lsp.models.ReferenceResult
import com.tom.rv2ide.lsp.models.SignatureHelp
import com.tom.rv2ide.lsp.models.SignatureHelpParams
import com.tom.rv2ide.models.Range
import com.tom.rv2ide.projects.IWorkspace
import java.nio.file.Path
import org.slf4j.LoggerFactory

/**
 * Language server for C/C++ using clangd.
 *
 * @author Mohammed-baqer-null @ https://github.com/Mohammed-baqer-null
 */
class ClangLanguageServer(private val context: Context) : ILanguageServer {

  companion object {
    const val SERVER_ID = "ide.lsp.clang"
    private val log = LoggerFactory.getLogger(ClangLanguageServer::class.java)
  }

  private var _client: ILanguageClient? = null

  override val serverId: String = SERVER_ID

  override val client: ILanguageClient?
    get() = _client

  override fun connectClient(client: ILanguageClient?) {
    _client = client
  }

  override fun applySettings(settings: IServerSettings?) {
    // No-op: clangd settings handled externally
  }

  override fun setupWorkspace(workspace: IWorkspace) {
    // No-op: clangd connects to workspace via compile_commands.json
  }

  override fun complete(params: CompletionParams?): CompletionResult {
    return CompletionResult(emptyList())
  }

  override suspend fun findReferences(params: ReferenceParams): ReferenceResult {
    return ReferenceResult(emptyList())
  }

  override suspend fun findDefinition(params: DefinitionParams): DefinitionResult {
    return DefinitionResult(emptyList())
  }

  override suspend fun expandSelection(params: ExpandSelectionParams): Range {
    return params.selection
  }

  override suspend fun signatureHelp(params: SignatureHelpParams): SignatureHelp {
    return SignatureHelp(emptyList(), -1, -1)
  }

  override suspend fun hover(params: DefinitionParams): MarkupContent {
    return MarkupContent()
  }

  override suspend fun analyze(file: Path): DiagnosticResult {
    return DiagnosticResult.NO_UPDATE
  }

  override fun formatCode(params: FormatCodeParams?): CodeFormatResult {
    return CodeFormatResult(false, mutableListOf())
  }

  override fun handleFailure(failure: LSPFailure?): Boolean {
    log.error("Clang LSP failure: {}", failure?.error?.message)
    return false
  }

  override fun shutdown() {
    _client = null
    log.info("ClangLanguageServer shut down")
  }
}
