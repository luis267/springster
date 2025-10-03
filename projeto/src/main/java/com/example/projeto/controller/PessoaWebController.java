package com.example.projeto.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.projeto.model.Pessoa;
import com.example.projeto.service.PessoaService;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/pessoas")
public class PessoaWebController {

    private final PessoaService pessoaService;

    public PessoaWebController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    // Mapeia GET /pessoas para redirecionar para /pessoas/listar
    @GetMapping
    public String index() {
        return "redirect:/pessoas/listar";
    }

    // 1. Exibe a página de cadastro (formulário)
    @GetMapping("/cadastrar")
    public String exibirFormCadastro(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        return "pessoas/form";
    }

    // 2. Processa o envio do formulário de cadastro
    @PostMapping("/cadastrar")
    public String cadastrarPessoa(
            @Valid @ModelAttribute("pessoa") Pessoa pessoa,
            BindingResult result,
            RedirectAttributes ra) {

        if (result.hasErrors()) {
            return "pessoas/form"; // Volta para o formulário se houver erro
        }

        pessoaService.salvarPessoa(pessoa);
        ra.addFlashAttribute("success", "Pessoa cadastrada com sucesso!");
        return "redirect:/pessoas/listar";
    }

    // 3. Exibe a página de listagem
    @GetMapping("/listar")
    public String listarPessoas(Model model) {
        model.addAttribute("lista", pessoaService.listarPessoas());
        return "pessoas/lista";
    }

    // 4. Exibe a página de detalhes de uma pessoa
    @GetMapping("/{id}")
    public String detalhesPessoa(@PathVariable Long id, Model model) {
        Pessoa p = pessoaService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pessoa não encontrada, id: " + id));
        model.addAttribute("pessoa", p);
        return "pessoas/detalhe";
    }

    // 5. Processa a exclusão de uma pessoa
    @PostMapping("/{id}/excluir")
    public String excluirPessoa(@PathVariable Long id, RedirectAttributes ra) {
        pessoaService.deletarPessoa(id);
        ra.addFlashAttribute("success", "Pessoa excluída com sucesso!");
        return "redirect:/pessoas/listar";
    }
}