/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CAttachmentDetailComponent from '@/entities/c-attachment/c-attachment-details.vue';
import CAttachmentClass from '@/entities/c-attachment/c-attachment-details.component';
import CAttachmentService from '@/entities/c-attachment/c-attachment.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CAttachment Management Detail Component', () => {
    let wrapper: Wrapper<CAttachmentClass>;
    let comp: CAttachmentClass;
    let cAttachmentServiceStub: SinonStubbedInstance<CAttachmentService>;

    beforeEach(() => {
      cAttachmentServiceStub = sinon.createStubInstance<CAttachmentService>(CAttachmentService);

      wrapper = shallowMount<CAttachmentClass>(CAttachmentDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cAttachmentService: () => cAttachmentServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCAttachment = { id: 123 };
        cAttachmentServiceStub.find.resolves(foundCAttachment);

        // WHEN
        comp.retrieveCAttachment(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cAttachment).toBe(foundCAttachment);
      });
    });
  });
});
