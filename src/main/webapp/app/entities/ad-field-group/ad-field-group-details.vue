<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="aDFieldGroup">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.aDFieldGroup.detail.title')">ADFieldGroup</span> {{aDFieldGroup.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.aDFieldGroup.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{aDFieldGroup.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDFieldGroup.collapsible')">Collapsible</span>
                    </dt>
                    <dd>
                        <span>{{aDFieldGroup.collapsible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDFieldGroup.collapseByDefault')">Collapse By Default</span>
                    </dt>
                    <dd>
                        <span>{{aDFieldGroup.collapseByDefault}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDFieldGroup.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{aDFieldGroup.active}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="aDFieldGroup.id" :to="{name: 'ADFieldGroupEdit', params: {aDFieldGroupId: aDFieldGroup.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-field-group-details.component.ts">
</script>
