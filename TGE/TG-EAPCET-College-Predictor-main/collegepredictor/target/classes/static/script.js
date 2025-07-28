document.getElementById('predictorForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const rank = document.getElementById('rank').value;
    const gender = document.querySelector('input[name="gender"]:checked').value;
    const phase = document.querySelector('input[name="phase"]:checked').value;
    const branch = document.getElementById('branch').value;

    fetch(`/api/colleges/predict?rank=${rank}&gender=${gender}&phase=${phase}${branch ? `&branch=${branch}` : ''}`)
        .then(response => response.json())
        .then(colleges => {
            const collegeList = document.getElementById('collegeList');
            collegeList.innerHTML = '';

            if (colleges.length === 0) {
                collegeList.innerHTML = '<div class="list-group-item">No colleges found matching your criteria.</div>';
                return;
            }

            colleges.forEach(college => {
                const collegeItem = document.createElement('div');
                collegeItem.className = 'list-group-item';
                collegeItem.innerHTML = `
                    <h5>${college.name} (${college.code})</h5>
                    <p><strong>Branch:</strong> ${college.branchName} (${college.branchCode})</p>
                    <p><strong>Rank Range:</strong> ${college.openingRank} - ${college.closingRank}</p>
                    <p><strong>Phase:</strong> ${college.phase}</p>
                `;
                collegeList.appendChild(collegeItem);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('collegeList').innerHTML =
                '<div class="list-group-item text-danger">Error fetching college data. Please try again.</div>';
        });
});