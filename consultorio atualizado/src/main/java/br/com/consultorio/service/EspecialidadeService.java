package br.com.consultorio.service;

import br.com.consultorio.entity.Convenio;
import br.com.consultorio.entity.Especialidacao;
import br.com.consultorio.repository.EspecializacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecializacaoRepository especialidadeRepository;




    //findby / selectbyid
    public Optional<Especialidacao> selectById(Long id) {
        return especialidadeRepository.findById(id);
    }


    //selectall no formato de paginas, para facilitar a paginacao
    public Page<Especialidacao> selectAll(Pageable pageable) {
        return especialidadeRepository.findAll(pageable);
    }

    @Transactional
    public void updateExcluido(Long id, Especialidacao especialidacao) {
        if (id == especialidacao.getId()) {
            this.especialidadeRepository.updateStatusExcluido(id, false);
        } else {
            throw new RuntimeException();
        }
    }

    //update
    @Transactional
    public void updateEspecialidade(Long id, Especialidacao especialidacao) {
        if (id == especialidacao.getId()) {
            especialidadeRepository.save(especialidacao);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void insertEspecialidade(Especialidacao especialidacao) {
        especialidadeRepository.save(especialidacao);
    }
}
