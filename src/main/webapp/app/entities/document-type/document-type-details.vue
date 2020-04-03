<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="documentType">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.documentType.detail.title')">DocumentType</span> {{documentType.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.documentType.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{documentType.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.documentType.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{documentType.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.documentType.hasExpirationDate')">Has Expiration Date</span>
                    </dt>
                    <dd>
                        <span>{{documentType.hasExpirationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.documentType.forCompany')">For Company</span>
                    </dt>
                    <dd>
                        <span>{{documentType.forCompany}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.documentType.forProfessional')">For Professional</span>
                    </dt>
                    <dd>
                        <span>{{documentType.forProfessional}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.documentType.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{documentType.active}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="documentType.id" :to="{name: 'DocumentTypeEdit', params: {documentTypeId: documentType.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./document-type-details.component.ts">
</script>
