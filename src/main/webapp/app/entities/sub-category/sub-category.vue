<template>
  <div>
    <h2 id="page-heading" data-cy="SubCategoryHeading">
      <span v-text="$t('elonlarApp.subCategory.home.title')" id="sub-category-heading">Sub Categories</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('elonlarApp.subCategory.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'SubCategoryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-sub-category"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('elonlarApp.subCategory.home.createLabel')"> Create a new Sub Category </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && subCategories && subCategories.length === 0">
      <span v-text="$t('elonlarApp.subCategory.home.notFound')">No subCategories found</span>
    </div>
    <div class="table-responsive" v-if="subCategories && subCategories.length > 0">
      <table class="table table-striped" aria-describedby="subCategories">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nameUzLat')">
              <span v-text="$t('elonlarApp.subCategory.nameUzLat')">Name Uz Lat</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameUzLat'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nameUzKr')">
              <span v-text="$t('elonlarApp.subCategory.nameUzKr')">Name Uz Kr</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameUzKr'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nameRu')">
              <span v-text="$t('elonlarApp.subCategory.nameRu')">Name Ru</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameRu'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nameEn')">
              <span v-text="$t('elonlarApp.subCategory.nameEn')">Name En</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameEn'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('category.id')">
              <span v-text="$t('elonlarApp.subCategory.category')">Category</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'category.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="subCategory in subCategories" :key="subCategory.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SubCategoryView', params: { subCategoryId: subCategory.id } }">{{ subCategory.id }}</router-link>
            </td>
            <td>{{ subCategory.nameUzLat }}</td>
            <td>{{ subCategory.nameUzKr }}</td>
            <td>{{ subCategory.nameRu }}</td>
            <td>{{ subCategory.nameEn }}</td>
            <td>
              <div v-if="subCategory.category">
                <router-link :to="{ name: 'CategoryView', params: { categoryId: subCategory.category.id } }">{{
                  subCategory.category.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SubCategoryView', params: { subCategoryId: subCategory.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SubCategoryEdit', params: { subCategoryId: subCategory.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(subCategory)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="elonlarApp.subCategory.delete.question" data-cy="subCategoryDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-subCategory-heading" v-text="$t('elonlarApp.subCategory.delete.question', { id: removeId })">
          Are you sure you want to delete this Sub Category?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-subCategory"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeSubCategory()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="subCategories && subCategories.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./sub-category.component.ts"></script>
