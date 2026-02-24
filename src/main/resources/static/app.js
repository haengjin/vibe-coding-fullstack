const state = {
  page: 1,
  size: 5,
  totalPages: 1,
  currentPostId: null,
  editMode: false,
};

const listView = document.getElementById("list-view");
const detailView = document.getElementById("detail-view");
const formView = document.getElementById("form-view");

const listArea = document.getElementById("list-area");
const pageLabel = document.getElementById("page-label");
const prevBtn = document.getElementById("prev-btn");
const nextBtn = document.getElementById("next-btn");
const newBtn = document.getElementById("new-btn");

const detailTitle = document.getElementById("detail-title");
const detailTags = document.getElementById("detail-tags");
const detailMeta = document.getElementById("detail-meta");
const detailContent = document.getElementById("detail-content");
const backBtn = document.getElementById("back-btn");
const editBtn = document.getElementById("edit-btn");
const deleteBtn = document.getElementById("delete-btn");

const formTitle = document.getElementById("form-title");
const postForm = document.getElementById("post-form");
const titleInput = document.getElementById("title");
const contentInput = document.getElementById("content");
const tagsInput = document.getElementById("tags");
const cancelBtn = document.getElementById("cancel-btn");

async function request(url, options = {}) {
  const res = await fetch(url, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });
  if (!res.ok) {
    let msg = `요청 실패 (${res.status})`;
    try {
      const err = await res.json();
      if (err.message) msg = err.message;
    } catch (_) {}
    throw new Error(msg);
  }
  if (res.status === 204) return null;
  return res.json();
}

function show(view) {
  listView.classList.add("hidden");
  detailView.classList.add("hidden");
  formView.classList.add("hidden");
  view.classList.remove("hidden");
}

function route() {
  const hash = location.hash || "#list";
  if (hash.startsWith("#detail/")) {
    const id = Number(hash.split("/")[1]);
    openDetail(id);
    return;
  }
  if (hash.startsWith("#edit/")) {
    const id = Number(hash.split("/")[1]);
    openEditForm(id);
    return;
  }
  if (hash === "#new") {
    openCreateForm();
    return;
  }
  openList();
}

async function openList() {
  show(listView);
  const data = await request(`/api/posts?page=${state.page}&size=${state.size}`);
  state.totalPages = Math.max(data.totalPages || 1, 1);
  pageLabel.textContent = `${state.page} / ${state.totalPages}`;

  const rows = (data.items || []).map((p) => `
    <tr class="hover:bg-white/5 transition-colors group">
      <td class="px-6 py-4 text-sm text-slate-400 font-mono">${p.no}</td>
      <td class="px-6 py-4">
        <a class="text-slate-200 font-semibold group-hover:text-primary transition-colors" href="#detail/${p.no}">
          ${escapeHtml(p.title)}
        </a>
      </td>
      <td class="px-6 py-4 text-sm text-slate-500">${formatDate(p.createdAt)}</td>
      <td class="px-6 py-4 text-sm text-slate-500">${p.views}</td>
    </tr>
  `).join("");

  listArea.innerHTML = rows || `
    <tr>
      <td colspan="4" class="px-6 py-8 text-sm text-slate-500 text-center">게시글이 없습니다.</td>
    </tr>
  `;

  prevBtn.disabled = state.page <= 1;
  nextBtn.disabled = state.page >= state.totalPages;
}

async function openDetail(id) {
  state.currentPostId = id;
  show(detailView);
  const post = await request(`/api/posts/${id}`);
  detailTitle.textContent = post.title;
  detailTags.textContent = post.tags ? `# ${post.tags}` : "";
  detailMeta.textContent = `No. ${post.no} / 조회수 ${post.views} / ${formatDate(post.createdAt)}`;
  detailContent.textContent = post.content || "";
}

function openCreateForm() {
  state.editMode = false;
  state.currentPostId = null;
  formTitle.textContent = "게시글 작성";
  titleInput.value = "";
  contentInput.value = "";
  tagsInput.value = "";
  show(formView);
}

async function openEditForm(id) {
  state.editMode = true;
  state.currentPostId = id;
  formTitle.textContent = "게시글 수정";
  const post = await request(`/api/posts/${id}`);
  titleInput.value = post.title || "";
  contentInput.value = post.content || "";
  tagsInput.value = post.tags || "";
  show(formView);
}

async function submitForm(e) {
  e.preventDefault();
  const payload = {
    title: titleInput.value.trim(),
    content: contentInput.value,
    tags: tagsInput.value,
  };

  if (state.editMode && state.currentPostId) {
    const updated = await request(`/api/posts/${state.currentPostId}`, {
      method: "PATCH",
      body: JSON.stringify(payload),
    });
    location.hash = `#detail/${updated.no}`;
  } else {
    const created = await request("/api/posts", {
      method: "POST",
      body: JSON.stringify(payload),
    });
    location.hash = `#detail/${created.no}`;
  }
}

async function removePost() {
  if (!state.currentPostId) return;
  if (!confirm("정말 삭제하시겠습니까?")) return;
  await request(`/api/posts/${state.currentPostId}`, { method: "DELETE" });
  location.hash = "#list";
}

function formatDate(v) {
  if (!v) return "-";
  const d = new Date(v);
  if (Number.isNaN(d.getTime())) return "-";
  return d.toISOString().slice(0, 10);
}

function escapeHtml(s) {
  return String(s ?? "")
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;");
}

prevBtn.addEventListener("click", () => {
  if (state.page > 1) {
    state.page -= 1;
    openList();
  }
});

nextBtn.addEventListener("click", () => {
  if (state.page < state.totalPages) {
    state.page += 1;
    openList();
  }
});

newBtn.addEventListener("click", () => {
  location.hash = "#new";
});

backBtn.addEventListener("click", () => {
  location.hash = "#list";
});

editBtn.addEventListener("click", () => {
  location.hash = `#edit/${state.currentPostId}`;
});

deleteBtn.addEventListener("click", removePost);

cancelBtn.addEventListener("click", () => {
  location.hash = state.currentPostId ? `#detail/${state.currentPostId}` : "#list";
});

postForm.addEventListener("submit", submitForm);
window.addEventListener("hashchange", route);

route();
