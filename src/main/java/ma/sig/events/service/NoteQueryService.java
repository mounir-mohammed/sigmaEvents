package ma.sig.events.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Note;
import ma.sig.events.repository.NoteRepository;
import ma.sig.events.security.AuthoritiesConstants;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.criteria.NoteCriteria;
import ma.sig.events.service.dto.NoteDTO;
import ma.sig.events.service.mapper.NoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LongFilter;

/**
 * Service for executing complex queries for {@link Note} entities in the database.
 * The main input is a {@link NoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NoteDTO} or a {@link Page} of {@link NoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NoteQueryService extends QueryService<Note> {

    private final Logger log = LoggerFactory.getLogger(NoteQueryService.class);

    private final NoteRepository noteRepository;

    private final NoteMapper noteMapper;

    private final UserService userService;

    public NoteQueryService(NoteRepository noteRepository, NoteMapper noteMapper, UserService userService) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    /**
     * Return a {@link List} of {@link NoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NoteDTO> findByCriteria(NoteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Note> specification = createSpecification(criteria);
        return noteMapper.toDto(noteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NoteDTO> findByCriteria(NoteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Note> specification = createSpecification(criteria);
        return noteRepository.findAll(specification, page).map(noteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NoteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Note> specification = createSpecification(criteria);
        return noteRepository.count(specification);
    }

    /**
     * Function to convert {@link NoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Note> createSpecification(NoteCriteria criteria) {
        Specification<Note> specification = Specification.where(null);
        //ADD FILTER START
        Optional<User> currentUser = null;
        try {
            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.ADMIN)) {
                currentUser = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get().toString());
                if (currentUser.isPresent() && currentUser.get().getPrintingCentre().getEvent().getEventId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                new LongFilter().setEquals(currentUser.get().getPrintingCentre().getEvent().getEventId()),
                                root -> root.join(Note_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(new LongFilter().setEquals(0L), root -> root.join(Note_.event, JoinType.LEFT).get(Event_.eventId))
                );
        }
        //ADD FILTER END
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getNoteId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoteId(), Note_.noteId));
            }
            if (criteria.getNoteValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoteValue(), Note_.noteValue));
            }
            if (criteria.getNoteDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoteDescription(), Note_.noteDescription));
            }
            if (criteria.getNoteTypeParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoteTypeParams(), Note_.noteTypeParams));
            }
            if (criteria.getNoteTypeAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoteTypeAttributs(), Note_.noteTypeAttributs));
            }
            if (criteria.getNoteStat() != null) {
                specification = specification.and(buildSpecification(criteria.getNoteStat(), Note_.noteStat));
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(Note_.accreditation, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(Note_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
        }
        return specification;
    }

    public Optional<NoteDTO> findByIdCheckEvent(Long id) {
        log.debug("find NoteDTO by id and check event : {}", id);
        final Specification<Note> specification = createEventSpecification(id);
        return noteRepository.findOne(specification).map(noteMapper::toDto);
    }

    private Specification<Note> createEventSpecification(Long id) {
        Specification<Note> specification = Specification.where(null);
        specification = specification.and(buildSpecification(new LongFilter().setEquals(id), Note_.noteId));
        //ADD FILTER START
        Optional<User> currentUser = null;
        try {
            if (!SecurityUtils.hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.ADMIN)) {
                currentUser = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get().toString());
                if (currentUser.isPresent() && currentUser.get().getPrintingCentre().getEvent().getEventId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                new LongFilter().setEquals(currentUser.get().getPrintingCentre().getEvent().getEventId()),
                                root -> root.join(Note_.event, JoinType.LEFT).get(Event_.eventId)
                            )
                        );
                }
            }
        } catch (Exception e) {
            specification =
                specification.and(
                    buildSpecification(new LongFilter().setEquals(0L), root -> root.join(Note_.event, JoinType.LEFT).get(Event_.eventId))
                );
        }
        return specification;
    }
}
