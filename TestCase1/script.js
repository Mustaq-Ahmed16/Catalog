const fs = require('fs');

// Function to decode value with given base
function decodeValue(base, value) {
  return parseInt(value, base);
}

// Function to perform Lagrange Interpolation
function lagrangeInterpolation(points) {
  const k = points.length;
  let c = 0; // This will hold the constant term (c)

  for (let i = 0; i < k; i++) {
    let [x_i, y_i] = points[i];

    // Calculate the Lagrange basis polynomial L_i(0)
    let L_i = 1;
    for (let j = 0; j < k; j++) {
      if (i !== j) {
        L_i *= (0 - points[j][0]) / (x_i - points[j][0]);
      }
    }

    // Add to the constant term
    c += y_i * L_i;
  }

  return c;
}

// Function to process input JSON data
function processInput(input) {
  const n = input.keys.n;
  const k = input.keys.k;

  const points = [];
  for (let i = 1; i <= n; i++) {
    if (input[i]) {
      const base = parseInt(input[i].base);
      const value = input[i].value;

      const x = i; // x is the key
      const y = decodeValue(base, value); // Decode y value

      points.push([x, y]); // Push (x, y) to points
    }
  }

  return points.slice(0, k); // Return only the first k points
}

// Reading the JSON file asynchronously
fs.readFile('data.json', 'utf8', (err, data) => {
  if (err) {
    console.error('Error reading the file:', err);
    return;
  }
  const input = JSON.parse(data);

  // Process input and compute the constant term
  const points = processInput(input);
  const c = lagrangeInterpolation(points);

  // Output the constant term
  console.log(c);
});


// const fs = require('fs');

// // Reading the JSON file asynchronously
// fs.readFile('data.json', 'utf8', (err, data) => {
//   if (err) {
//     console.error('Error reading the file:', err);
//     return;
//   }
//   const jsonData = JSON.parse(data);
//   console.log(jsonData); // Access the JSON data here
//   // Example: Accessing n and k values
//   const n = jsonData.keys.n;
//   const k = jsonData.keys.k;
//   console.log(`n: ${n}, k: ${k}`);
// });
// const fs = require('fs');
// function decodeValue(base, value) {
//   return parseInt(value, base);
// }

// function lagrangeInterpolation(points) {
//   const k = points.length;
//   let c = 0; // This will hold the constant term (c)

//   for (let i = 0; i < k; i++) {
//     let [x_i, y_i] = points[i];

//     // Calculate the Lagrange basis polynomial L_i(0)
//     let L_i = 1;
//     for (let j = 0; j < k; j++) {
//       if (i !== j) {
//         L_i *= (0 - points[j][0]) / (x_i - points[j][0]);
//       }
//     }

//     // Add to the constant term
//     c += y_i * L_i;
//   }

//   return c;
// }

// function processInput(input) {
//   const n = input.keys.n;
//   const k = input.keys.k;

//   const points = [];
//   for (let i = 1; i <= n; i++) {
//     if (input[i]) {
//       const base = parseInt(input[i].base);
//       const value = input[i].value;

//       const x = i; // x is the key
//       const y = decodeValue(base, value); // Decode y value

//       points.push([x, y]); // Push (x, y) to points
//     }
//   }

//   return points.slice(0, k); // Return only the first k points
// }

// // Sample input in JSON format


// // Reading the JSON file asynchronously
// fs.readFile('data.json', 'utf8', (err, data)) => {
//   if (err) {
//     console.error('Error reading the file:', err);
//     return;
//   }
//   const input = JSON.parse(data);

//   // Process input and compute the constant term
//   const points = processInput(input);
//   const c = lagrangeInterpolation(points);

//   // Output the constant term
//   console.log(c);