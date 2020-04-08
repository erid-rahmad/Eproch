<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="businessCategory">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.businessCategory.detail.title')">BusinessCategory</span> {{businessCategory.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.businessCategory.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{businessCategory.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.businessCategory.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{businessCategory.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.businessCategory.parentCategory')">Parent Category</span>
                    </dt>
                    <dd>
                        <div v-if="businessCategory.parentCategoryId">
                            <router-link :to="{name: 'BusinessCategoryDetails', params: {businessCategoryId: businessCategory.parentCategoryId}}">{{businessCategory.parentCategoryId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="businessCategory.id" :to="{name: 'BusinessCategoryUpdate', params: {businessCategoryId: businessCategory.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./business-category-details.component.ts">
</script>
